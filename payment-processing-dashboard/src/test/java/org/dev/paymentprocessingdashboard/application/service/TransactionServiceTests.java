package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TransactionServiceTests {

    @Mock
    private ITransactionPersistencePort transactionPersistenceAdapter;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Filter transactions with valid criteria returns non-empty page")
    void filterTransactionsWithValidCriteriaReturnsNonEmptyPage() {

        Transaction transaction = new Transaction("id", "userid", 10.0, "status","timestamp", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionPersistenceAdapter.findAll(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionService.filterTransactions("status", "userId", 10.0, 100.0, "transactionId", 0, 10);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions with no matching criteria returns empty page")
    void filterTransactionsWithNoMatchingCriteriaReturnsEmptyPage() {
        Page<Transaction> expectedPage = Page.empty();
        when(transactionPersistenceAdapter.findAll(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionService.filterTransactions(null, null, null, null, null, 0, 10);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions with maximum page size limits results")
    void filterTransactionsWithMaximumPageSizeLimitsResults() {
        Transaction transaction = new Transaction("id", "userid", 10.0, "status","timestamp", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.nCopies(5, transaction));
        when(transactionPersistenceAdapter.findAll(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionService.filterTransactions("status", "userId", 10.0, 100.0, "transactionId", 0, 5);

        assertEquals(5, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions with invalid page number returns first page")
    void filterTransactionsWithInvalidPageNumberReturnsFirstPage() {
        Transaction transaction = new Transaction("id", "userid", 10.0, "status","timestamp", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionPersistenceAdapter.findAll(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionService.filterTransactions("status", "userId", 10.0, 100.0, "transactionId", -1, 10);

        assertEquals(1, result.getTotalElements());
    }
}