package org.dev.paymentnotificationhubs.application;

import org.dev.paymentnotificationhubs.domain.Transaction;

public interface INotificationStrategy {
    boolean applies(Transaction transaction);
    String getMessage(Transaction transaction);
}