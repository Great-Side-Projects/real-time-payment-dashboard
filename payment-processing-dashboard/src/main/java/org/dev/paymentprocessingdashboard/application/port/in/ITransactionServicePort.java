package org.dev.paymentprocessingdashboard.application.port.in;

import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ITransactionServicePort {
    Page<Transaction> filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size);
    List<Transaction> processTransaction(List<Transaction> transactions);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionPerMinuteSummary summaryTransactionsPerMinute();
}
