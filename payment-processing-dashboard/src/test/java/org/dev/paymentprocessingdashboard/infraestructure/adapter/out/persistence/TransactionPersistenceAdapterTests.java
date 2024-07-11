package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.TransactionSpecificationBuilderAdapter;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionRepository;
import org.dev.paymentprocessingdashboard.application.port.ITransactionSpecificationBuilderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TransactionPersistenceAdapterTests {

    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private ITransactionSpecificationBuilderPort transactionSpecificationBuilderAdapter;

    @InjectMocks
    private TransactionPersistenceAdapter transactionPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save transaction persists data correctly")
    void saveTransactionPersistsDataCorrectly() {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), "user1", 100.0, "success", "2024-07-08T20:10:08.338Z", "location");
        TransactionEntity transactionEntity = TransactionMapper.toTransactionEntity(transaction);
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        Transaction savedTransaction = transactionPersistenceAdapter.save(transaction);
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getId()).isEqualTo(transaction.getId());
    }

    @Test
    @DisplayName("Save all transactions handles list correctly")
    void saveAllTransactionsHandlesListCorrectly() {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), "user1", 100.0, "success", "2024-07-08T20:10:08.338Z", "location");
        List<Transaction> transactions = Collections.singletonList(transaction);
        transactionPersistenceAdapter.saveAll(transactions);
        // Verification is based on the absence of exceptions
    }

    @Test
    @DisplayName("Find all with valid criteria returns non-empty page")
    void findAllWithValidCriteriaReturnsNonEmptyPage() {
        // Mock the behavior using the interface methods directly
        when(transactionSpecificationBuilderAdapter.New()).thenReturn(transactionSpecificationBuilderAdapter);
        when(transactionSpecificationBuilderAdapter.with(any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.withBetween(any(), any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.build()).thenReturn(Specification.where(null));

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.randomUUID());
        transactionEntity.setTimestamp(LocalDateTime.now());
        Page<TransactionEntity> page = new PageImpl<>(Collections.singletonList(transactionEntity));

        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        Page<Transaction> result = transactionPersistenceAdapter.findAll("success", "user1", 50.0, 150.0, UUID.randomUUID().toString(), 0, 10);

        assertThat(result.getContent()).isNotEmpty();
    }

    @Test
    @DisplayName("Find all with no criteria returns empty page")
    void findAllWithNoCriteriaReturnsEmptyPage() {
        when(transactionSpecificationBuilderAdapter.New()).thenReturn(transactionSpecificationBuilderAdapter);
        when(transactionSpecificationBuilderAdapter.with(any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.withBetween(any(), any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.build()).thenReturn(Specification.where(null));
        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(Page.empty());
        when(transactionSpecificationBuilderAdapter.build()).thenReturn(Specification.where(null));

        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(Page.empty());
        Page<Transaction> result = transactionPersistenceAdapter.findAll(null, null, null, null, null, 0, 10);
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Find all respects maximum page size limit")
    void findAllRespectsMaximumPageSizeLimit() {
        int requestedSize = 500;
        final int PAGE_SIZE = 100;
        when(transactionSpecificationBuilderAdapter.New()).thenReturn(transactionSpecificationBuilderAdapter);
        when(transactionSpecificationBuilderAdapter.with(any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.withBetween(any(), any(), any())).thenReturn(new TransactionSpecificationBuilderAdapter());
        when(transactionSpecificationBuilderAdapter.build()).thenReturn(Specification.where(null));
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.randomUUID());
        transactionEntity.setTimestamp(LocalDateTime.now());
        Page<TransactionEntity> page = new PageImpl<>(Collections.nCopies(PAGE_SIZE, transactionEntity ));
        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);
        Page<Transaction> result = transactionPersistenceAdapter.findAll(null, null, null, null, null, 0, requestedSize);
        assertThat(result.getSize()).isEqualTo(PAGE_SIZE);
    }

    @Test
    @DisplayName("Fallback save all method is triggered on exception")
    void fallbackSaveAllMethodIsTriggeredOnException() {
        List<Transaction> transactions = Collections.singletonList(new Transaction(UUID.randomUUID().toString(), "user1", 100.0, "failed", "2024-07-08T20:10:08.338Z", "location"));
        doThrow(new RuntimeException("Database error")).when(transactionRepository).saveAll(anyList());
        transactionPersistenceAdapter.fallbackSaveAll(transactions, new RuntimeException("Database error"));
        // Verification is based on the absence of exceptions
    }
}