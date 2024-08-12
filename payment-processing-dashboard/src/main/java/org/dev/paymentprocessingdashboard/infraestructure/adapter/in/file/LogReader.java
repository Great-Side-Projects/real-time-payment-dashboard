package org.dev.paymentprocessingdashboard.infraestructure.adapter.in.file;

import org.dev.paymentprocessingdashboard.application.port.in.ILogReaderServicePort;
import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LogReader
{
    private final ITransactionServicePort transactionService;
    private final ILogReaderServicePort logReaderService;
    private final String ANSIRESET = "\u001B[0m";
    private final String ANSIBLUE = "\u001B[34m";

    public LogReader(ITransactionServicePort transactionService, ILogReaderServicePort logReaderService) {
        this.transactionService = transactionService;
        this.logReaderService = logReaderService;
    }

    @Scheduled(fixedRate = 2500) // Cada 1 segundos
    public void readTransactionLogFile() throws IOException {

        long startTime = System.currentTimeMillis();
        List<Transaction> transactions = logReaderService.readTransactionLogFile();
        if (transactions.isEmpty()) {
            return;
        }

        transactionService.processTransaction(transactions);

        System.out.println(ANSIRESET + transactionService.totalTransactionSummary());
        System.out.println(transactionService.summaryTransactionsPerMinute());

        System.out.println("Number of transactions processed: " + ANSIBLUE + transactions.size() + ANSIRESET);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Time duration: " + duration / 60000 + ":" + (duration % 60000) / 1000 + "." + (duration % 1000));
        System.out.println("***************************************");
        System.out.println();
        logReaderService.saveLastKnownPosition();
    }
}
