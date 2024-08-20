package org.dev.paymentprocessing.application.service;

import org.dev.paymentprocessing.application.port.ITransactionServicePort;
import org.dev.paymentprocessing.application.port.out.ITransactionEventStreamingPort;
import org.dev.paymentprocessing.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessing.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessing.common.UseCase;
import org.dev.paymentprocessing.domain.TotalTransactionSummary;
import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.event.Event;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;
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
    public List<Transaction> processTransaction(Event<TransactionReceivedEvent> transactionReceivedEvent) {
        if (transactionReceivedEvent == null) {
            return List.of();
        }

        List<Transaction> transactions = (List<Transaction>) transactionReceivedEvent.getData();
        //event id
        long startTime = System.currentTimeMillis();
        transactionPersistenceAdapter.saveAll(transactions, transactionReceivedEvent.getId());
        long endTime = System.currentTimeMillis();
        //transactionEventStreamingAdapter.sendProcessedEvent(transactions);
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
