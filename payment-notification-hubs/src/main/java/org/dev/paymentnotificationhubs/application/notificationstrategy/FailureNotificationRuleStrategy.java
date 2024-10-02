package org.dev.paymentnotificationhubs.application.notificationstrategy;

import org.dev.paymentnotificationhubs.application.INotificationRuleStrategy;
import org.dev.paymentnotificationhubs.domain.Transaction;
import org.dev.paymentnotificationhubs.domain.TransactionStatusEnum;

public class FailureNotificationRuleStrategy implements INotificationRuleStrategy {

    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatusEnum.FAILURE.toString());
    }

    @Override
    public String getMessage(Transaction transaction) {
        return """
                {
                 "notificationtype": "failurenotification",
                 "eventid": "%s",
                """ + transaction.toString() + """     
                }
                """;
    }
}