package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionRepository;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class TransactionPersistenceAdapter implements ITransactionPersistencePort {
    private final ITransactionRepository transactionRepository;

    public TransactionPersistenceAdapter(ITransactionRepository urlRepository, ITransactionRepository transactionRepository) {
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
    public TotalTransactionSummary TotalTransactionSummary() {
        TotalTransactionSummary totalTransactionSummary = new TotalTransactionSummary(transactionRepository.findTransactionSummary());
        return totalTransactionSummary;
    }

    @Override
    public TotalTransactionPerMinuteSummary SummaryTransactionsPerMinute() {
        List<TransactionPerMinuteSummaryProjection> transactionPerMinuteSummariesProjection = transactionRepository.findTransactionPerMinuteSummary();
        List<TransactionPerMinuteSummary> totalTransactionPerMinuteSummary = transactionPerMinuteSummariesProjection.stream()
                                                                            .map(TransactionMapper::toTransactionPerMinuteSummary)
                                                                            .collect(Collectors.toList());
        return new TotalTransactionPerMinuteSummary(totalTransactionPerMinuteSummary);
    }
}
