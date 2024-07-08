package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.Transaction;

import java.util.List;

public interface ITransactionSendNotificationPort {
    void send(List<Transaction> transactions);
}
