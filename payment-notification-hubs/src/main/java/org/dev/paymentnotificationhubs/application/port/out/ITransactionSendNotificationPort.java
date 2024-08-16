package org.dev.paymentnotificationhubs.application.port.out;

import org.dev.paymentnotificationhubs.domain.Transaction;
import java.util.List;

public interface ITransactionSendNotificationPort {
    List<Transaction> send(List<Transaction> transactions);
}
