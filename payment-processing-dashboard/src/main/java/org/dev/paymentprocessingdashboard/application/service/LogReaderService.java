package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessingdashboard.application.port.in.ILogReaderServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class LogReaderService implements ILogReaderServicePort {

    private static final String LOG_FILE_PATH = "log-generator/logs/transactions.log";
    private final Path path = Paths.get(LOG_FILE_PATH);
    private long lastKnownPosition = 0;
    private final String ANSIRESET = "\u001B[0m";
    private final String ANSIBLUE = "\u001B[34m";
    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionFormatProviderPort transactionFormatProviderAdapter;
    private final ITransactionSendNotificationPort transactionSendNotificationAdapter;

    public LogReaderService(ITransactionPersistencePort transactionPersistenceAdapter, ITransactionFormatProviderPort transactionFormatProviderAdapter, ITransactionSendNotificationPort transactionSendNotificationAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionFormatProviderAdapter = transactionFormatProviderAdapter;
        this.transactionSendNotificationAdapter = transactionSendNotificationAdapter;
    }

    @Scheduled(fixedRate = 500) // Cada 1 segundos
    @Override
    public List<Transaction> readLogFile() throws IOException {

        try (RandomAccessFile file = new RandomAccessFile(path.normalize().toAbsolutePath().toFile(), "r")) {
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
                return List.of();
            }

            transactionPersistenceAdapter.saveAll(transactions);
            transactionSendNotificationAdapter.send(transactions);

            transactions.forEach(transaction -> System.out.println(ANSIBLUE + transaction));
            System.out.println(ANSIRESET + transactionPersistenceAdapter.totalTransactionSummary());
            System.out.println(transactionPersistenceAdapter.summaryTransactionsPerMinute());
            lastKnownPosition = file.getFilePointer();
            return transactions;
        }
    }
}

