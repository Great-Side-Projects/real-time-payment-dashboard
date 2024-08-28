package org.dev.paymentprocessing.application.port.out;

import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;

import java.util.List;

public interface ILoggingAspectPort {
    void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    void logProcessTransaction(TransactionReceivedEvent transactionReceivedEvent);
}
