package org.dev.paymentprocessing.infraestructure.adapter.in.web;

import org.dev.paymentprocessing.application.port.ITransactionServicePort;
import org.dev.paymentprocessing.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessing.domain.TotalTransactionSummary;
import org.dev.paymentprocessing.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    @DisplayName("Filter transactions with valid parameters")
    void filterTransactionsWithValidParameters() {
        TransactionFilterResponse response = new TransactionFilterResponse(
                Collections.singletonList(new Transaction
                        ("626ccc13-df07-46f8-acc2-d1ed011271a1", "U1", 100.0, "success", "2024-07-22T23:17:20.694Z", "CN")),
                "pagingState",
                false,
                1
        );
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), any(), anyInt())).thenReturn(response);

        TransactionFilterResponse result = transactionController.filterTransactions("COMPLETED", "user123", 100.0, 1000.0, "txn123", "pagingState", 50);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Filter transactions with empty parameters")
    void filterTransactionsWithEmptyParameters() {
        TransactionFilterResponse response = new TransactionFilterResponse(
                Collections.singletonList(new Transaction
                        ("626ccc13-df07-46f8-acc2-d1ed011271a1", "U1", 100.0, "success", "2024-07-22T23:17:20.694Z", "CN")),
                "pagingState",
                false,
                1
        );
        when(transactionService.filterTransactions(any(), any(), any(), any(), any(), any(), anyInt())).thenReturn(response);

        TransactionFilterResponse result = transactionController.filterTransactions(null, null, null, null, null, null, 10);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Process transactions with valid list")
    void processTransactionsWithValidList() {
        List<Transaction> transactions = Collections.singletonList(new Transaction(
                "626ccc13-df07-46f8-acc2-d1ed011271a1",
                "U1",
                100.0,
                "success",
                "2024-07-22T23:17:20.694Z",
                "CN"));
        when(transactionService.processTransaction(transactions)).thenReturn(anyList());

        transactionController.processTransaction(transactions);
        verify(transactionService, times(1)).processTransaction(transactions);
    }

    @Test
    @DisplayName("Process transactions with empty list")
    void processTransactionsWithEmptyList() {
        List<Transaction> transactions = Collections.emptyList();
        when(transactionService.processTransaction(transactions)).thenReturn(anyList());

        transactionController.processTransaction(transactions);
        verify(transactionService, times(1)).processTransaction(transactions);
    }

    @Test
    @DisplayName("Total transaction summary")
    void totalTransactionSummary() {
        TotalTransactionSummary summary = new TotalTransactionSummary(100,50000);
        when(transactionService.totalTransactionSummary()).thenReturn(summary);

        TotalTransactionSummary result = transactionController.totalTransactionSummary();
        assertNotNull(result);
    }

    @Test
    @DisplayName("Total transaction summary by status")
    void totalTransactionSummaryByStatus() {
        TotalTransactionSummary summary = new TotalTransactionSummary(100,50000);
        when(transactionService.getTransactionSummaryByStatus(anyString())).thenReturn(summary);

        TotalTransactionSummary result = transactionController.totalTransactionSummaryByStatus("COMPLETED");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Total transaction summary by user id")
    void totalTransactionSummaryByUserId() {
        TotalTransactionSummary summary = new TotalTransactionSummary(100,50000);
        when(transactionService.getTransactionSummaryByUserId(anyString())).thenReturn(summary);

        TotalTransactionSummary result = transactionController.totalTransactionSummaryByUserId("user123");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Transactions per minute summary")
    void summaryTransactionsPerMinute() {
        //TotalTransactionPerMinuteSummary summary = new TotalTransactionPerMinuteSummary();
        //when(transactionService.summaryTransactionsPerMinute()).thenReturn(summary);

        //TotalTransactionPerMinuteSummary result = transactionController.summaryTransactionsPerMinute();
        //assertNotNull(result);
    }
}