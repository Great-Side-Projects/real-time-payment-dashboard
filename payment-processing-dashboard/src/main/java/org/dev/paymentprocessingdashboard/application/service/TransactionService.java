package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventStreamingPort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistenceAdapter;
    private final ITransactionEventStreamingPort<List<Transaction>> transactionEventStreamingAdapter;

    public TransactionService(ITransactionPersistencePort transactionPersistenceAdapter,
                              ITransactionEventStreamingPort<List<Transaction>> transactionEventStreamingAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.transactionEventStreamingAdapter = transactionEventStreamingAdapter;
    }

    @Override
    public TransactionFilterResponse filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {
        //validation Business Logic

        Slice<Transaction> transactionSlice = transactionPersistenceAdapter.findAll(status, userId, minAmount, maxAmount, transactionId, nextPagingState, size);
        boolean hasNext = transactionSlice.hasNext();

        return new TransactionFilterResponse(
                transactionSlice.getContent(),
                hasNext ? transactionPersistenceAdapter.getNextPagingState(transactionSlice.getPageable()) : "",
                hasNext,
                transactionSlice.getNumberOfElements()
        );
    }

    @Transactional
    @Override
    public List<Transaction> processTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return List.of();
        }

        long startTime = System.currentTimeMillis();
        transactionPersistenceAdapter.saveAll(transactions);
        long endTime = System.currentTimeMillis();
        transactionEventStreamingAdapter.sendProcessedEvent(transactions);
        System.out.println("Time elapsed: " + (endTime - startTime) / 60000 + ":" + ((endTime - startTime) / 1000) % 60 + ":" + (endTime - startTime) % 1000);
        return transactions;
    }

    @Override
    public TotalTransactionSummary totalTransactionSummary() {
        return transactionPersistenceAdapter.getTransactionSummary();
    }

    @Override
    public TotalTransactionSummary getTransactionSummaryByStatus(String status) {
        return transactionPersistenceAdapter.getTransactionSummaryByStatus(status);
    }

    @Override
    public TotalTransactionSummary getTransactionSummaryByUserId(String userId) {
        return transactionPersistenceAdapter.getTransactionSummaryByUserId(userId);
    }
}
