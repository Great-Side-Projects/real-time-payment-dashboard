package org.dev.paymentnotificationhubs.application.service;

import org.dev.paymentnotificationhubs.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentnotificationhubs.common.UseCase;
import org.dev.paymentnotificationhubs.domain.Transaction;
import org.dev.paymentnotificationhubs.domain.event.Event;
import org.dev.paymentnotificationhubs.domain.event.TransactionProcessedEvent;
import java.util.List;

@UseCase
public class TransactionService {

    private final ITransactionSendNotificationPort transactionSendNotificationAdapter;

    public TransactionService(ITransactionSendNotificationPort transactionSendNotificationAdapter) {
        this.transactionSendNotificationAdapter = transactionSendNotificationAdapter;
    }

    public void transactionProcessedEvent (Event<TransactionProcessedEvent> transactionProcessedEvent) {
        // send notification hub to email, sms, push notification, etc
        List<Transaction> transactions = (List<Transaction>)transactionProcessedEvent.getData();
        transactionSendNotificationAdapter.send(transactions);
    }
}
