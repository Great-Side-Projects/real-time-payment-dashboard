package org.dev.paymentprocessingdashboard.application.port.out;


import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionPersistencePort {
    Transaction save(Transaction transaction);
    void saveAll(List<Transaction> transactions);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionPerMinuteSummary summaryTransactionsPerMinute();
    Page<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size);
}
