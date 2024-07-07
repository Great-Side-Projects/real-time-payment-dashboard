package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

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

    public TransactionPersistenceAdapter(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        transactionRepository.save(TransactionMapper.toTransactionEntity(transaction));
        return transaction;
    }

    @Override
    public void saveAll(List<Transaction> transactions) {
        List<TransactionEntity> transactionEntityList = transactions.stream()
                .map(TransactionMapper::toTransactionEntity)
                .collect(Collectors.toList());
        transactionRepository.saveAll(transactionEntityList);
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
        Specification<TransactionEntity> spec = Specification.where(null);

        //Todo: Refactor this to a more elegant way
        
        if (status != null && !status.isEmpty()) {
            spec = spec.and(TransactionSpecification.hasStatus(status));
        }
        if (userId != null && !userId.isEmpty()) {
            spec = spec.and(TransactionSpecification.hasUserId(userId));
        }
        if (minAmount != null && maxAmount != null) {
            spec = spec.and(TransactionSpecification.hasAmountBetween(minAmount, maxAmount));
        }
        if (transactionId != null && !transactionId.isEmpty()) {
            spec = spec.and(TransactionSpecification.hasTransactionId(UUID.fromString(transactionId)));
        }
        if (size > PAGE_SIZE)
            size = PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(spec, pageable)
                .map(TransactionMapper::toTransaction);
    }
}
