package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Slice;

import java.nio.ByteBuffer;
import java.util.List;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionSendNotificationPort transactionSendNotificationAdapter;

    public TransactionService(ITransactionPersistencePort transactionPersistenceAdapter, ITransactionSendNotificationPort transactionSendNotificationAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionSendNotificationAdapter = transactionSendNotificationAdapter;
    }

    @Override
    public TransactionFilterResponse filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {
        //validation Business Logic

        Slice<Transaction> transactionSlice = transactionPersistenceAdapter.findAll(status, userId, minAmount, maxAmount, transactionId, nextPagingState, size);
        boolean hasNext = transactionSlice.hasNext();
        TransactionFilterResponse transactionFilterResponse = new TransactionFilterResponse(
                transactionSlice.getContent(),
                hasNext ? transactionPersistenceAdapter.getNextPagingState(transactionSlice.getPageable()) : "",
                hasNext,
                transactionSlice.getNumberOfElements()
        );

        return transactionFilterResponse;
    }

    @Override
    public List<Transaction> processTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return List.of();
        }

        long startTime = System.currentTimeMillis();
        transactionPersistenceAdapter.saveAll(transactions);
        long endTime = System.currentTimeMillis();
        transactionSendNotificationAdapter.send(transactions);
        System.out.println("Time elapsed: " + (endTime - startTime) / 60000 + ":" + ((endTime - startTime) / 1000) % 60 + ":" + (endTime - startTime) % 1000);
        return transactions;
    }

    @Override
    public TotalTransactionSummary totalTransactionSummary() {
        return transactionPersistenceAdapter.totalTransactionSummary();
    }

    @Override
    public TotalTransactionPerMinuteSummary summaryTransactionsPerMinute() {
        return transactionPersistenceAdapter.summaryTransactionsPerMinute();
    }
}
