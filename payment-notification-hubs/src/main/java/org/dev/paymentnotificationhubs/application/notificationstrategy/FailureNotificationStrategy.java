package org.dev.paymentnotificationhubs.application.notificationstrategy;

import org.dev.paymentnotificationhubs.application.INotificationStrategy;
import org.dev.paymentnotificationhubs.domain.Transaction;
import org.dev.paymentnotificationhubs.domain.TransactionStatusEnum;

public class FailureNotificationStrategy implements INotificationStrategy {

    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatusEnum.FAILURE.toString());
    }

    @Override
    public String getMessage(Transaction transaction) {
        return "FailureNotification: " + transaction.toString();
    }
}
