package org.dev.paymentprocessing.infraestructure.adapter;

import org.dev.paymentprocessing.application.port.ITransactionFormatProviderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionFormatProviderAdapterTest {

    private TransactionFormatProviderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new TransactionFormatProviderAdapter();
    }

    @Test
    @DisplayName("Extract transaction details from well-formed log line")
    void extractTransactionDetailsFromWellFormedLogLine() {
        String logLine = "Log: 2024-07-04T15:33:20.537Z - Transaction [e6c1434e-9c5f-4dde-9b01-ec60daa8ff0d] - User: U1, Amount: $4306, Status: success, Time: 2024-07-04T15:33:20.537Z, Location: NY";
        ITransactionFormatProviderPort.rTransaction result = adapter.getTransactionFromLine(logLine);
        assertNotNull(result);
        assertEquals("2024-07-04T15:33:20.537Z", result.logTime());
        assertEquals("e6c1434e-9c5f-4dde-9b01-ec60daa8ff0d", result.transactionId());
        assertEquals("U1", result.userId());
        assertEquals(4306, result.amount());
        assertEquals("success", result.status());
        assertEquals("2024-07-04T15:33:20.537Z", result.timestamp());
        assertEquals("NY", result.location());
    }

    @Test
    @DisplayName("Return null for log line not matching pattern")
    void returnNullForLogLineNotMatchingPattern() {
        String logLine = "Invalid log line format";
        ITransactionFormatProviderPort.rTransaction result = adapter.getTransactionFromLine(logLine);
        assertNull(result);
    }

    @Test
    @DisplayName("Handle log line with missing parts gracefully")
    void handleLogLineWithMissingPartsGracefully() {
        String logLine = "Log: 2024-07-04T15:33:20.537Z - Transaction [] - User: , Amount: $, Status: , Time: , Location: ";
        ITransactionFormatProviderPort.rTransaction result = adapter.getTransactionFromLine(logLine);
        assertNull(result);
    }
}