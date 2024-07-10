package org.dev.paymentprocessingdashboard.application.port;

import org.dev.paymentprocessingdashboard.domain.Transaction;

public interface INotificationStrategy {
    boolean applies(Transaction transaction);
    String getMessage(Transaction transaction);
}
