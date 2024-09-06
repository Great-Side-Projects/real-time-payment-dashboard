package org.dev.paymentingestion.application.service;

import org.dev.paymentingestion.application.port.in.IFileReaderPort;
import org.dev.paymentingestion.application.port.in.ILogReaderServicePort;
import org.dev.paymentingestion.application.port.out.ITransactionFormatProviderPort;
import org.dev.paymentingestion.common.UseCase;
import org.dev.paymentingestion.domain.Transaction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@UseCase
public class LogReaderService implements ILogReaderServicePort {

    private final ITransactionFormatProviderPort transactionFormatProviderAdapter;

    //***disabled for web controller****
    //private final IFileReaderPort fileReaderAdapter;

    public LogReaderService(ITransactionFormatProviderPort transactionFormatProviderAdapter
    //                        IFileReaderPort fileReaderAdapter
    ) {

        this.transactionFormatProviderAdapter = transactionFormatProviderAdapter;
    //    this.fileReaderAdapter = fileReaderAdapter;
    }

    @Override
    public List<Transaction> readTransactionLogFile() throws IOException {

        List<String> lines = new ArrayList<>();//fileReaderAdapter.readLines();

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
        //fileReaderAdapter.saveLastKnownPosition();
    }
}

