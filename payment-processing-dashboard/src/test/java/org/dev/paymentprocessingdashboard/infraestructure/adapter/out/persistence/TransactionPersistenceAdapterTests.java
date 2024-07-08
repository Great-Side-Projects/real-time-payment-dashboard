package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionRepository;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionPersistenceAdapterTests {

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private TransactionPersistenceAdapter transactionPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save transaction returns the same transaction")
    void saveTransactionReturnsTheSameTransaction() {
        String id = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(id, "user1", 10.0, "success", "2024-07-08T20:10:08.338Z", "location");
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.fromString(id));
        transactionEntity.setUserid("user1");
        transactionEntity.setAmount(10.0);
        transactionEntity.setStatus("success");
        transactionEntity.setLocation("location");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(transaction.getTimestamp(), formatter);
        transactionEntity.setTimestamp(zonedDateTime.toLocalDateTime());
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        Transaction result = transactionPersistenceAdapter.save(transaction);
        assertEquals(transaction, result);
    }

    @Test
    @DisplayName("Save all transactions successfully")
    void saveAllTransactionsSuccessfully() {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), "user1", 10.0, "success", "2024-07-08T20:10:08.338Z", "location");
        List<Transaction> transactions = Collections.singletonList(transaction);
        transactionPersistenceAdapter.saveAll(transactions);
        // Success is verified by no exceptions thrown
    }

    @Test
    @DisplayName("Find all transactions with valid criteria returns non-empty page")
    void findAllTransactionsWithValidCriteriaReturnsNonEmptyPage() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.fromString(UUID.randomUUID().toString()));
        transactionEntity.setUserid("user2");
        transactionEntity.setAmount(20.0);
        transactionEntity.setStatus("success");
        transactionEntity.setLocation("location");
        transactionEntity.setTimestamp(LocalDateTime.now());
        Page<TransactionEntity> expectedPage = new PageImpl<>(Collections.singletonList(transactionEntity));
        when(transactionRepository.findAll(any(), any(PageRequest.class))).thenReturn(expectedPage);
        Page<Transaction> result = transactionPersistenceAdapter.findAll("completed", "user1", 10.0, 100.0, UUID.randomUUID().toString(), 0, 10);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Find all transactions with no matching criteria returns empty page")
    void findAllTransactionsWithNoMatchingCriteriaReturnsEmptyPage() {
        Page<TransactionEntity> expectedPage = Page.empty();
        when(transactionRepository.findAll(any(), any(PageRequest.class))).thenReturn(expectedPage);
        Page<Transaction> result = transactionPersistenceAdapter.findAll(null, null, null, null, null, 0, 10);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    @DisplayName("Find all transactions with page size exceeding maximum limits results to default page size")
    void findAllTransactionsWithPageSizeExceedingMaximumLimitsResultsToDefaultPageSize() {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.fromString(UUID.randomUUID().toString()));
        transactionEntity.setUserid("user2");
        transactionEntity.setAmount(20.0);
        transactionEntity.setStatus("success");
        transactionEntity.setLocation("location");
        transactionEntity.setTimestamp(LocalDateTime.now());
        Page<TransactionEntity> expectedPage = new PageImpl<>(Collections.nCopies(100, transactionEntity));
        when(transactionRepository.findAll(any(), any(PageRequest.class))).thenReturn(expectedPage);
        Page<Transaction> result = transactionPersistenceAdapter.findAll("completed", "user2", 20.0, 200.0, UUID.randomUUID().toString(), 0, 500);
        assertEquals(100, result.getTotalElements());
    }
}
