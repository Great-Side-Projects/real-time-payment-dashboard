package org.dev.paymentingestion.application.service;

import org.dev.paymentingestion.application.port.in.ITransactionServicePort;
import org.dev.paymentingestion.application.port.out.ITransactionEventStreamingPort;
import org.dev.paymentingestion.common.UseCase;
import org.dev.paymentingestion.domain.Transaction;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ITransactionEventStreamingPort<List<Transaction>> transactionEventStreamingAdapter;

    public TransactionService(ITransactionEventStreamingPort<List<Transaction>> transactionEventStreamingAdapter) {
        this.transactionEventStreamingAdapter = transactionEventStreamingAdapter;
    }

    @Transactional
    @Override
    public void processTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }
        long startTime = System.currentTimeMillis();
        transactionEventStreamingAdapter.sendReceivedEvent(transactions);
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (endTime - startTime) / 60000 + ":" + ((endTime - startTime) / 1000) % 60 + ":" + (endTime - startTime) % 1000);
    }
}
