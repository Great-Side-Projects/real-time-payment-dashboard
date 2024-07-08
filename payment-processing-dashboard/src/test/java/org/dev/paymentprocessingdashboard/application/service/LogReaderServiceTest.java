package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;
import org.dev.paymentprocessingdashboard.application.port.in.IFileReaderPort;
import org.dev.paymentprocessingdashboard.application.port.in.ILogReaderServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(fileReaderAdapter.readLines(anyLong())).thenReturn(Collections.emptyList());

        List<Transaction> result = logReaderService.readLogFile();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("ReadLogFile with valid lines should process transactions")
    void readLogFileWithValidLinesShouldProcessTransactions() throws IOException {
        Transaction transaction = new Transaction("id", "userid", 10.0, "status","timestamp", "location");
        List<String> lines = Arrays.asList(
                "Log: 2024-07-08T20:10:08.338Z - Transaction [0d5b93e0-ae87-4ceb-9839-79f1c62915ba] - User: U1, Amount: $1336, Status: success, Time: 2024-07-08T20:10:08.338Z, Location: NY",
                "Log: 2024-07-08T20:10:08.338Z - Transaction [2e84f631-fbc5-47c8-b337-68fa4b53b032] - User: U2, Amount: $4833, Status: success, Time: 2024-07-08T20:10:08.338Z, Location: CA");
        when(fileReaderAdapter.readLines(anyLong())).thenReturn(lines);
        when(transactionFormatProviderAdapter.getTransactionFromLine(anyString())).thenReturn(new ITransactionFormatProviderPort.rTransaction("2024-07-08T20:10:08.338Z", "0d5b93e0-ae87-4ceb-9839-79f1c62915ba", "U1", 1336.0, "success", "2024-07-08T20:10:08.338Z", "NY"));
        //when(Transaction.processLine(anyString(),transactionFormatProviderAdapter)).thenReturn(transaction);
        List<Transaction> result = logReaderService.readLogFile();
        assertEquals(2, result.size());
        verify(transactionPersistenceAdapter, times(1)).saveAll(anyList());
        verify(transactionSendNotificationAdapter, times(1)).send(anyList());
    }

    @Test
    @DisplayName("ReadLogFile with invalid lines should skip processing")
    void readLogFileWithInvalidLinesShouldSkipProcessing() throws IOException {
        when(fileReaderAdapter.readLines(anyLong())).thenReturn(Arrays.asList("invalidLine1", "invalidLine2"));
        when(Transaction.processLine(anyString(),transactionFormatProviderAdapter)).thenReturn(null);

        List<Transaction> result = logReaderService.readLogFile();

        assertEquals(0, result.size());
        verify(transactionPersistenceAdapter, never()).saveAll(anyList());
        verify(transactionSendNotificationAdapter, never()).send(anyList());
    }

    @Test
    @DisplayName("ReadLogFile updates lastKnownPosition after reading lines")
    void readLogFileUpdatesLastKnownPositionAfterReadingLines() throws IOException {
        Transaction transaction = new Transaction("0d5b93e0-ae87-4ceb-9839-79f1c62915ba", "U1", 1336.0, "success", "2024-07-08T20:10:08.338Z", "NY");
        when(fileReaderAdapter.readLines(anyLong())).thenReturn(Collections.singletonList("Log: 2024-07-08T20:10:08.338Z - Transaction [0d5b93e0-ae87-4ceb-9839-79f1c62915ba] - User: U1, Amount: $1336, Status: success, Time: 2024-07-08T20:10:08.338Z, Location: NY"));
        when(transactionFormatProviderAdapter.getTransactionFromLine(anyString())).thenReturn(new ITransactionFormatProviderPort.rTransaction("2024-07-08T20:10:08.338Z", "0d5b93e0-ae87-4ceb-9839-79f1c62915ba", "U1", 1336.0, "success", "2024-07-08T20:10:08.338Z", "NY"));
        //when(Transaction.processLine(anyString(),transactionFormatProviderAdapter)).thenReturn(transaction);
        when(fileReaderAdapter.getLastKnownPosition()).thenReturn(100L);
        logReaderService.readLogFile();
        assertEquals(100, fileReaderAdapter.getLastKnownPosition());
    }
}
