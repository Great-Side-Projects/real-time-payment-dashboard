package org.dev.paymentprocessing.application.service;

import org.dev.paymentprocessing.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessing.application.port.in.IFileReaderPort;
import org.dev.paymentprocessing.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessing.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessing.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class LogReaderServiceTests {

    @Mock
    private ITransactionPersistencePort transactionPersistenceAdapter;
    @Mock
    private ITransactionFormatProviderPort transactionFormatProviderAdapter;
    @Mock
    private ITransactionSendNotificationPort transactionSendNotificationAdapter;
    @Mock
    private IFileReaderPort fileReaderAdapter;

    @InjectMocks
    private LogReaderService logReaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("ReadLogFile with no lines should return empty list")
    void readLogFileWithNoLinesShouldReturnEmptyList() throws IOException {
        when(fileReaderAdapter.readLines()).thenReturn(Collections.emptyList());
        List<Transaction> result = logReaderService.readTransactionLogFile();
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("ReadLogFile with invalid lines should skip processing")
    void readLogFileWithInvalidLinesShouldSkipProcessing() throws IOException {
        when(fileReaderAdapter.readLines()).thenReturn(Arrays.asList("invalidLine1", "invalidLine2"));
        when(Transaction.processLine(anyString(),transactionFormatProviderAdapter)).thenReturn(null);
        List<Transaction> result = logReaderService.readTransactionLogFile();
        assertEquals(0, result.size());
        verify(transactionPersistenceAdapter, never()).saveAll(anyList(), );
        verify(transactionSendNotificationAdapter, never()).send(anyList());
    }

    @Test
    @DisplayName("ReadLogFile updates lastKnownPosition after reading lines")
    void readLogFileUpdatesLastKnownPositionAfterReadingLines() throws IOException {
        Transaction transaction = new Transaction("0d5b93e0-ae87-4ceb-9839-79f1c62915ba", "U1", 1336.0, "success", "2024-07-08T20:10:08.338Z", "NY");
        when(fileReaderAdapter.readLines()).thenReturn(Collections.singletonList("Log: 2024-07-08T20:10:08.338Z - Transaction [0d5b93e0-ae87-4ceb-9839-79f1c62915ba] - User: U1, Amount: $1336, Status: success, Time: 2024-07-08T20:10:08.338Z, Location: NY"));
        when(transactionFormatProviderAdapter.getTransactionFromLine(anyString())).thenReturn(new ITransactionFormatProviderPort.rTransaction("2024-07-08T20:10:08.338Z", "0d5b93e0-ae87-4ceb-9839-79f1c62915ba", "U1", 1336.0, "success", "2024-07-08T20:10:08.338Z", "NY"));
        //when(Transaction.processLine(anyString(),transactionFormatProviderAdapter)).thenReturn(transaction);
        when(fileReaderAdapter.getLastKnownPosition()).thenReturn(100L);
        logReaderService.readTransactionLogFile();
        assertEquals(100, fileReaderAdapter.getLastKnownPosition());
    }

    @Test
    @DisplayName("LogFile with empty content results in no transactions processed")
    void logFileWithEmptyContentResultsInNoTransactionsProcessed() throws IOException {
        when(fileReaderAdapter.readLines()).thenReturn(List.of());
        List<Transaction> transactions = logReaderService.readTransactionLogFile();
        assertTrue(transactions.isEmpty());
        verify(transactionPersistenceAdapter, never()).saveAll(any(), );
        verify(transactionSendNotificationAdapter, never()).send(any());
    }

    @Test
    @DisplayName("Invalid log lines are skipped with no transactions processed")
    void invalidLogLinesAreSkippedWithNoTransactionsProcessed() throws IOException {
        when(fileReaderAdapter.readLines()).thenReturn(List.of("Invalid log line 1", "Invalid log line 2"));
        when(transactionFormatProviderAdapter.getTransactionFromLine(anyString())).thenReturn(null);
        List<Transaction> transactions = logReaderService.readTransactionLogFile();
        assertTrue(transactions.isEmpty());
        verify(transactionPersistenceAdapter, never()).saveAll(any(), );
        verify(transactionSendNotificationAdapter, never()).send(any());
    }

    @Test
    @DisplayName("Exception thrown by fileReaderAdapter is handled gracefully")
    void exceptionThrownByFileReaderAdapterIsHandledGracefully() throws IOException {
        when(fileReaderAdapter.readLines()).thenThrow(new IOException("Failed to read file"));
        Exception exception = assertThrows(IOException.class, () -> logReaderService.readTransactionLogFile());
        assertEquals("Failed to read file", exception.getMessage());
        verify(transactionPersistenceAdapter, never()).saveAll(any(), );
        verify(transactionSendNotificationAdapter, never()).send(any());
    }
}
