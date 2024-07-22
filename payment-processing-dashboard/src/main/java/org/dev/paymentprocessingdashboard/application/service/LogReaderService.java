package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessingdashboard.application.port.in.IFileReaderPort;
import org.dev.paymentprocessingdashboard.application.port.in.ILogReaderServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@UseCase
public class LogReaderService implements ILogReaderServicePort {

    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionFormatProviderPort transactionFormatProviderAdapter;

    private final IFileReaderPort fileReaderAdapter;

    public LogReaderService(ITransactionPersistencePort transactionPersistenceAdapter,
                            ITransactionFormatProviderPort transactionFormatProviderAdapter,
                            IFileReaderPort fileReaderAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionFormatProviderAdapter = transactionFormatProviderAdapter;
        this.fileReaderAdapter = fileReaderAdapter;
    }

    @Override
    public List<Transaction> readTransactionLogFile() throws IOException {

        List<String> lines = fileReaderAdapter.readLines();

        if (lines.isEmpty()) {
            return List.of();
        }

        //print new line
        System.out.println("***************************************");
        System.out.println("*** Transactions read from file ***");
        System.out.println("Number of transactions read: " + lines.size());

        List<Transaction> transactions = lines.parallelStream()
                .map(line -> Transaction.processLine(line, transactionFormatProviderAdapter))
                .filter(transaction -> transaction != null)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        return transactions;
    }

    @Override
    public void saveLastKnownPosition() throws IOException {
        fileReaderAdapter.saveLastKnownPosition();
    }
}

