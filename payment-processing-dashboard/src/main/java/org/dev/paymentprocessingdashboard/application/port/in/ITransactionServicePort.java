package org.dev.paymentprocessingdashboard.application.port.in;

import org.dev.paymentprocessingdashboard.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import java.util.List;

public interface ITransactionServicePort {
    TransactionFilterResponse filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    List<Transaction> processTransaction(List<Transaction> transactions);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionSummary getTransactionSummaryByStatus(String status);
    TotalTransactionSummary getTransactionSummaryByUserId(String userId);
}
