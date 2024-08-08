package org.dev.paymentprocessingdashboard.application.port.out;


import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.nio.ByteBuffer;
import java.util.List;

public interface ITransactionPersistencePort {
    Transaction save(Transaction transaction);
    void saveAll(List<Transaction> transactions);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionPerMinuteSummary summaryTransactionsPerMinute();
    Slice<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    String getNextPagingState(Pageable cassandraPageRequest);
}
