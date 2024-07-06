package org.dev.paymentprocessingdashboard.application.port.in;

import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;

public interface ITransactionServicePort {
    Page<Transaction> filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size);
}
