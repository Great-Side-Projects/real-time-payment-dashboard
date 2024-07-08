package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessingdashboard.application.port.in.IFileReaderPort;
import org.dev.paymentprocessingdashboard.application.port.in.ILogReaderServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class LogReaderService implements ILogReaderServicePort {

    private long lastKnownPosition = 0;
    private final String ANSIRESET = "\u001B[0m";
    private final String ANSIBLUE = "\u001B[34m";
    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionFormatProviderPort transactionFormatProviderAdapter;
    private final ITransactionSendNotificationPort transactionSendNotificationAdapter;
    private final IFileReaderPort fileReaderAdapter;

    public LogReaderService(ITransactionPersistencePort transactionPersistenceAdapter,
                            ITransactionFormatProviderPort transactionFormatProviderAdapter,
                            ITransactionSendNotificationPort transactionSendNotificationAdapter,
                            IFileReaderPort fileReaderAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionFormatProviderAdapter = transactionFormatProviderAdapter;
        this.transactionSendNotificationAdapter = transactionSendNotificationAdapter;
        this.fileReaderAdapter = fileReaderAdapter;
    }

    @Scheduled(fixedRate = 500) // Cada 1 segundos
    @Override
    public List<Transaction> readLogFile() throws IOException {

        List<String> lines = fileReaderAdapter.readLines(lastKnownPosition);

        if (lines.isEmpty()) {
            return List.of();
        }

        List<Transaction> transactions = new ArrayList<>();

        for (String line : lines) {
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
        lastKnownPosition = fileReaderAdapter.getLastKnownPosition();
        return transactions;

    }
}

