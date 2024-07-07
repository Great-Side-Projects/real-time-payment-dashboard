package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class LogReaderService {

    private static final String LOG_FILE_PATH = "../../../../log-generator/logs/transactions.log";
    private final Path path = Paths.get(LOG_FILE_PATH);
    private long lastKnownPosition = 0;
    private final String ANSIBLUE = "\u001B[34m";
    private final String ANSIRESET = "\u001B[0m";
    private final String ANSIRED = "\u001B[31m";
    private final String ANSIYELLOW = "\u001B[33m";
    private final ITransactionEventTemplatePort<String> transactionWebSocketAdapter;
    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionFormatProviderPort transactionFormatProviderAdapter;

    public LogReaderService(ITransactionPersistencePort transactionPersistenceAdapter, ITransactionEventTemplatePort<String> transactionWebSocketAdapter, ITransactionFormatProviderPort transactionFormatProviderAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionWebSocketAdapter = transactionWebSocketAdapter;
        this.transactionFormatProviderAdapter = transactionFormatProviderAdapter;
    }

    @Scheduled(fixedRate = 500) // Cada 1 segundos
    public List<Transaction> readLogFile() throws IOException {

        //Todo: read file with relative path
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            file.seek(lastKnownPosition);
            String line;
            List<Transaction> transactions = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                //Todo: processline in a separate thread to improve performance and the concert is by Transaction object, order by arrive line
                Transaction transaction = Transaction.processLine(line, transactionFormatProviderAdapter);
                if (transaction != null)
                    transactions.add(transaction);
            }
            if (transactions.isEmpty()) {
                //ahother way to return transactions empty
                return List.of();
            }
            transactionPersistenceAdapter.saveAll(transactions);
            transactions.forEach(transaction -> {
                System.out.println(ANSIBLUE + transaction.toString());

                if (transaction.getStatus().equals("failure")) {
                    transactionWebSocketAdapter.send(ANSIRED+transaction);
                }
                else if (transaction.getAmount() > 1000) {
                    transactionWebSocketAdapter.send(ANSIYELLOW+transaction);
                }
            });

           System.out.println(ANSIRESET+transactionPersistenceAdapter.totalTransactionSummary());
           System.out.println(transactionPersistenceAdapter.summaryTransactionsPerMinute());
           lastKnownPosition = file.getFilePointer();
           return transactions;
        }
    }
}

