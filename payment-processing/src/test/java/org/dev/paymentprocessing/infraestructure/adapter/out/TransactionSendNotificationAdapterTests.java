package org.dev.paymentprocessing.infraestructure.adapter.out;

import org.dev.paymentprocessing.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessing.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

class TransactionSendNotificationAdapterTests {

    @Mock
    private ITransactionEventTemplatePort<String> transactionWebSocketAdapter;

    @InjectMocks
    private TransactionSendNotificationAdapter transactionSendNotificationAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Sending notifications for multiple transactions includes failure and high amount")
    void sendingNotificationsForMultipleTransactionsIncludesFailureAndHighAmount() {
        Transaction failureTransaction = new Transaction("id", "userid", 10.0, "failure", "timestamp", "location");
        Transaction highAmountTransaction = new Transaction("id", "userid", 1500, "success", "timestamp", "location");
        transactionSendNotificationAdapter.send(Arrays.asList(failureTransaction, highAmountTransaction));
   verify(transactionWebSocketAdapter, times(2)).send(anyString());
    }

    @Test
    @DisplayName("Sending notifications for transactions without failure or high amount does not trigger notifications")
    void sendingNotificationsForTransactionsWithoutFailureOrHighAmountDoesNotTriggerNotifications() {
        Transaction normalTransaction = new Transaction("id", "userid", 500, "success", "timestamp", "location");
        transactionSendNotificationAdapter.send(Collections.singletonList(normalTransaction));
        verify(transactionWebSocketAdapter, never()).send(anyString());
    }

    @Test
    @DisplayName("Sending notification for failure transaction colors message red")
    void sendingNotificationForFailureTransactionColorsMessageRed() {
        Transaction failureTransaction = new Transaction("id", "userid", 500.0, "failure", "timestamp", "location");
        transactionSendNotificationAdapter.send(Collections.singletonList(failureTransaction));
        verify(transactionWebSocketAdapter).send(contains("\u001B[31m"));
    }

    @Test
    @DisplayName("Sending notification for high amount transaction colors message yellow")
    void sendingNotificationForHighAmountTransactionColorsMessageYellow() {
        Transaction highAmountTransaction = new Transaction("id", "userid", 1500.0, "success", "timestamp", "location");
        transactionSendNotificationAdapter.send(Collections.singletonList(highAmountTransaction));
        verify(transactionWebSocketAdapter).send(contains("\u001B[33m"));
    }
}