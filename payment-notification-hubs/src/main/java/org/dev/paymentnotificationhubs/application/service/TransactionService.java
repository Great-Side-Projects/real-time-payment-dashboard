package org.dev.paymentnotificationhubs.application.service;

import org.dev.paymentnotificationhubs.application.port.ITransactionServicePort;
import org.dev.paymentnotificationhubs.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentnotificationhubs.common.UseCase;
import org.dev.paymentnotificationhubs.domain.Transaction;
import org.dev.paymentnotificationhubs.domain.event.Event;
import org.dev.paymentnotificationhubs.domain.event.TransactionReceivedEvent;
import java.util.List;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ITransactionSendNotificationPort transactionSendNotificationAdapter;

    public TransactionService(ITransactionSendNotificationPort transactionSendNotificationAdapter) {
        this.transactionSendNotificationAdapter = transactionSendNotificationAdapter;
    }

    public void transactionReceivedEvent(TransactionReceivedEvent transactionReceivedEvent) {
        // send notification hub to email, sms, push notification, etc
        List<Transaction> transactions = transactionReceivedEvent.getData();
        transactionSendNotificationAdapter.send(transactions, transactionReceivedEvent.getId());
    }
}
