package org.dev.paymentprocessingdashboard.application.notificationstrategy;

import org.dev.paymentprocessingdashboard.application.port.INotificationStrategy;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.domain.TransactionStatusEnum;

public class FailureNotificationStrategy implements INotificationStrategy {

    private final String ANSIRED = "\u001B[31m";
    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatusEnum.FAILURE.toString());
    }

    @Override
    public String getMessage(Transaction transaction) {
        return ANSIRED + transaction.toString();
    }
}
