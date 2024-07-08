package org.dev.paymentprocessingdashboard.infraestructure.adapter.in.web;

import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
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

class TransactionControllerTests {

    @Mock
    private ITransactionServicePort transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Filter transactions returns non-empty page for valid criteria")
    void filterTransactionsReturnsNonEmptyPageForValidCriteria() {
        Transaction transaction = new Transaction("id", "user1", 10.0, "completed", "tx123", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionController.filterTransactions("completed", "user1", 10.0, 100.0, "tx123", 0, 10);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions returns empty page for no matching criteria")
    void filterTransactionsReturnsEmptyPageForNoMatchingCriteria() {
        Page<Transaction> expectedPage = Page.empty();
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionController.filterTransactions(null, null, null, null, null, 0, 10);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions handles large page size by limiting results")
    void filterTransactionsHandlesLargePageSizeByLimitingResults() {
        Transaction transaction = new Transaction("id", "user2", 20.0, "completed", "tx456", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.nCopies(50, transaction));
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionController.filterTransactions("completed", "user2", 20.0, 200.0, "tx456", 0, 50);

        assertEquals(50, result.getTotalElements());
    }

    @Test
    @DisplayName("Filter transactions with invalid page number defaults to first page")
    void filterTransactionsWithInvalidPageNumberDefaultsToFirstPage() {
        Transaction transaction = new Transaction("id", "user3", 5.0, "pending", "tx789", "location");
        Page<Transaction> expectedPage = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(expectedPage);

        Page<Transaction> result = transactionController.filterTransactions("pending", "user3", 5.0, 50.0, "tx789", -1, 10);

        assertEquals(1, result.getTotalElements());
    }
}