package org.dev.paymentnotificationhubs.application.notificationstrategy;

import org.dev.paymentnotificationhubs.application.INotificationStrategy;
import org.dev.paymentnotificationhubs.domain.Transaction;


public class HighAmountNotificationStrategy implements INotificationStrategy {

    private final int AMOUNT_1000 = 1000;
    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getAmount() > AMOUNT_1000;
    }

    @Override
    public String getMessage(Transaction transaction) {
       return "HighAmount notification: EventId: %s " + transaction.toString();
    }
}
