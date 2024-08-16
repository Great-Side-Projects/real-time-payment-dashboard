package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.Transaction;

import java.util.List;

public interface ILoggingAspectPort {
    void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    void logProcessTransaction(List<Transaction> transactions);
}
