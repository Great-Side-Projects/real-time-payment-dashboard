package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionRepository;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionTemplatePort;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.*;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.TransactionCriteriaBuilder;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class TransactionPersistenceAdapter implements ITransactionPersistencePort {
    private final ITransactionRepository transactionRepository;
    private static final int PAGE_SIZE = 100;
    private final ITransactionTemplatePort transactionTemplateAdapter;
    private static final String DEFAULT_CURSOR_MARK = "-1";

    public TransactionPersistenceAdapter(ITransactionRepository transactionRepository,
                                         ITransactionTemplatePort transactionTemplateAdapter) {
        this.transactionRepository = transactionRepository;
        this.transactionTemplateAdapter = transactionTemplateAdapter;
    }

    @Override
    public Transaction save(Transaction transaction) {
        transactionRepository.save(TransactionMapper.toTransactionEntity(transaction));
        return transaction;
    }

    @Override
    @CircuitBreaker(name = "transactionPersistence", fallbackMethod = "fallbackSaveAll")
    public void saveAll(List<Transaction> transactions) {
        try {
            List<TransactionEntity> transactionEntities = transactions.stream()
                    .map(TransactionMapper::toTransactionEntity)
                    .collect(Collectors.toList());
            transactionTemplateAdapter.saveAll(transactionEntities);

        } catch (Exception e) {
            throw new RuntimeException("Error saving transactions " + e.getMessage());
        }
    }

    public void fallbackSaveAll(List<Transaction> transactions, Throwable t) {
        //Implamentation of fallback method for saveAll method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error saving transactions: " + t.getMessage());
    }

    @Override
    public TotalTransactionSummary totalTransactionSummary() {
        TotalTransactionSummary totalTransactionSummary = new TotalTransactionSummary(transactionRepository.findTransactionSummary());
        return totalTransactionSummary;
    }

    @Override
    public TotalTransactionPerMinuteSummary summaryTransactionsPerMinute() {
        List<TransactionPerMinuteSummaryProjection> transactionPerMinuteSummariesProjection = transactionRepository.findTransactionPerMinuteSummary();
        List<TransactionPerMinuteSummary> totalTransactionPerMinuteSummary = transactionPerMinuteSummariesProjection.stream()
                .map(TransactionMapper::toTransactionPerMinuteSummary)
                .collect(Collectors.toList());
        return new TotalTransactionPerMinuteSummary(null);//totalTransactionPerMinuteSummary);
    }

    @Override
    public Slice<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {

        size = Math.min(size, PAGE_SIZE); // Ensure size does not exceed PAGE_SIZE
        CassandraPageRequest pageRequest = transactionTemplateAdapter.createCassandraPageRequest(size, nextPagingState);

        List<CriteriaDefinition> criterias = new TransactionCriteriaBuilder()
                .withStatus(status)
                .withUserId(userId)
                .withMinAmount(minAmount)
                .withMaxAmount(maxAmount)
                .withTransactionId(transactionId)
                .build();

        return transactionTemplateAdapter.findAll(pageRequest, criterias).map(TransactionMapper::toTransaction);
    }

    @Override
    public String getNextPagingState(Pageable cassandraPageRequest) {
        ByteBuffer pagingState = ((CassandraPageRequest)cassandraPageRequest).getPagingState();
        try {
            return com.datastax.oss.protocol.internal.util.Bytes.toHexString(pagingState);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing paging state " + e.getMessage());
        }
    }
}
