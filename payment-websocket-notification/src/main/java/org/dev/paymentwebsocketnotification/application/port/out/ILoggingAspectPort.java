package org.dev.paymentwebsocketnotification.application.port.out;

public interface ILoggingAspectPort {
    void logNotification(String transactionNotified);
}
