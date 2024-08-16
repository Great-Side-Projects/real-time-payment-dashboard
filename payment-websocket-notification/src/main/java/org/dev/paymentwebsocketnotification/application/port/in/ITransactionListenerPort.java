package org.dev.paymentwebsocketnotification.application.port.in;

public interface ITransactionListenerPort<T> {
    void TransactionWebsocketNotification(T transaction);
}
