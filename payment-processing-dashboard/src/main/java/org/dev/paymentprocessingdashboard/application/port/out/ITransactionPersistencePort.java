package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;

public interface ITransactionPersistencePort {
    Transaction save(Transaction transaction);
    void saveAll(List<Transaction> transactions);
    TotalTransactionSummary getTransactionSummary();
    TotalTransactionSummary getTransactionSummaryByStatus(String status);
    TotalTransactionSummary getTransactionSummaryByUserId(String userId);
    Slice<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    String getNextPagingState(Pageable cassandraPageRequest);

}
