package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentprocessingdashboard.application.port.ITransactionSpecificationBuilderPort;
import org.dev.paymentprocessingdashboard.application.port.out.IJdbcTemplatePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionRepository;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@PersistenceAdapter
public class TransactionPersistenceAdapter implements ITransactionPersistencePort {
    private final ITransactionRepository transactionRepository;
    private static final int PAGE_SIZE = 100;
    private final ITransactionSpecificationBuilderPort transactionSpecificationBuildeAdapter;
    private final IJdbcTemplatePort<Transaction> transactionJdbcTemplateAdapter;

    public TransactionPersistenceAdapter(ITransactionRepository transactionRepository,
                                         ITransactionSpecificationBuilderPort transactionSpecificationBuildeAdapter,
                                         IJdbcTemplatePort<Transaction> transactionJdbcTemplateAdapter) {
        this.transactionRepository = transactionRepository;
        this.transactionSpecificationBuildeAdapter = transactionSpecificationBuildeAdapter;
        this.transactionJdbcTemplateAdapter = transactionJdbcTemplateAdapter;
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
            transactionJdbcTemplateAdapter.saveAll(transactions);

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
        return new TotalTransactionPerMinuteSummary(totalTransactionPerMinuteSummary);
    }

    @Override
    public Page<Transaction> findAll(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size) {
        size = Math.min(size, PAGE_SIZE); // Ensure size does not exceed PAGE_SIZE
        Pageable pageable = PageRequest.of(page, size);

        UUID transactionIdUUID = transactionId != null && !transactionId.isEmpty() ? UUID.fromString(transactionId) : null;

        Specification<TransactionEntity> spec = transactionSpecificationBuildeAdapter.New()
                .with("status", status)
                .with("userid", userId)
                .withBetween("amount", minAmount, maxAmount)
                .with("id", transactionIdUUID)
                .build();

        return transactionRepository.findAll(spec, pageable).map(TransactionMapper::toTransaction);
    }
}
