package org.dev.paymentnotificationhubs.application.port.in.streaming;

public interface ITransactionListenerPort<T> {
    void transactionProcessedEvent(T transactionProcessedEventEvent);
}
