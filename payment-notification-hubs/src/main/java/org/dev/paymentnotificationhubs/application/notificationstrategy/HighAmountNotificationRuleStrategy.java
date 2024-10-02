package org.dev.paymentnotificationhubs.application.notificationstrategy;
import org.dev.paymentnotificationhubs.application.INotificationRuleStrategy;
import org.dev.paymentnotificationhubs.domain.Transaction;


public class HighAmountNotificationRuleStrategy implements INotificationRuleStrategy {

    private final int AMOUNT_1000 = 1000;

    @Override
    public boolean applies(Transaction transaction) {
        return transaction.getAmount() > AMOUNT_1000;
    }

    @Override
    public String getMessage(Transaction transaction) {
        return """
                {
                 "notificationtype": "highamountnotification",
                 "eventid": "%s",
                """ + transaction.toString() + """     
                }
                """;
    }
}
