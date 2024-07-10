package org.dev.paymentprocessingdashboard.application.notificationstrategy;

import org.dev.paymentprocessingdashboard.application.port.INotificationStrategy;
import org.dev.paymentprocessingdashboard.domain.Transaction;

public class HighAmountNotificationStrategy implements INotificationStrategy {

    private final int AMOUNT_1000 = 1000;
    private final String ANSIYELLOW = "\u001B[33m";
    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getAmount() > AMOUNT_1000;
    }

    @Override
    public String getMessage(Transaction transaction) {
       return ANSIYELLOW + transaction.toString();
    }
}
