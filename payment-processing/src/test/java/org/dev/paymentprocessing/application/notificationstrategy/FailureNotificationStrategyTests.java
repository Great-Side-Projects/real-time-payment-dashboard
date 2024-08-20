package org.dev.paymentprocessing.application.notificationstrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.TransactionStatusEnum;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FailureNotificationStrategyTest {

    private FailureNotificationStrategy failureNotificationStrategy;

    @BeforeEach
    void setUp() {
        failureNotificationStrategy = new FailureNotificationStrategy();
    }

    @Test
    @DisplayName("Applies returns true for transaction with FAILURE status")
    void appliesReturnsTrueForFailureStatus() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1500.0,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        assertTrue(failureNotificationStrategy.applies(transaction));
    }

    @Test
    @DisplayName("Applies returns false for transaction with SUCCESS status")
    void appliesReturnsFalseForSuccessStatus() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1000.0,
                TransactionStatusEnum.SUCCESS.toString(),
                "hoy",
                "US");
        assertFalse(failureNotificationStrategy.applies(transaction));
    }

    @Test
    @DisplayName("GetMessage returns red colored message for FAILURE transaction")
    void getMessageReturnsRedColoredMessageForFailureTransaction() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1000.0,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        String expectedStart = "\u001B[31m"; // ANSI red color code
        assertTrue(failureNotificationStrategy.getMessage(transaction).startsWith(expectedStart));
    }
}