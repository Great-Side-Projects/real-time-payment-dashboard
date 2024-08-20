package org.dev.paymentprocessing.application.notificationstrategy;

import org.dev.paymentprocessing.domain.TransactionStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.dev.paymentprocessing.domain.Transaction;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HighAmountNotificationStrategyTest {

    private HighAmountNotificationStrategy highAmountNotificationStrategy;

    @BeforeEach
    void setUp() {
        highAmountNotificationStrategy = new HighAmountNotificationStrategy();
    }

    @Test
    @DisplayName("Applies returns true for transaction amount greater than 1000")
    void appliesReturnsTrueForHighAmount() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1500,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        assertTrue(highAmountNotificationStrategy.applies(transaction));
    }

    @Test
    @DisplayName("Applies returns false for transaction amount equal to 1000")
    void appliesReturnsFalseForEqualAmount() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1000,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        assertFalse(highAmountNotificationStrategy.applies(transaction));
    }

    @Test
    @DisplayName("Applies returns false for transaction amount less than 1000")
    void appliesReturnsFalseForLowAmount() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 999,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        assertFalse(highAmountNotificationStrategy.applies(transaction));
    }

    @Test
    @DisplayName("GetMessage returns yellow colored message for high amount transaction")
    void getMessageReturnsYellowColoredMessageForHighAmountTransaction() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "123456", 1500,
                TransactionStatusEnum.FAILURE.toString(),
                "hoy",
                "US");

        String expectedStart = "\u001B[33m"; // ANSI yellow color code
        assertTrue(highAmountNotificationStrategy.getMessage(transaction).startsWith(expectedStart));
    }
}
