package org.dev.paymentprocessing.application.port.out;

import org.dev.paymentprocessing.domain.TotalTransactionSummary;
import org.dev.paymentprocessing.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;

public interface ITransactionPersistencePort {
    Transaction save(Transaction transaction, String eventId);
    void saveAll(List<Transaction> transactions, String eventId);
    TotalTransactionSummary getTransactionSummary();
    TotalTransactionSummary getTransactionSummaryByStatus(String status);
    TotalTransactionSummary getTransactionSummaryByUserId(String userId);
    Slice<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    String getNextPagingState(Pageable cassandraPageRequest);

}
