package org.dev.paymentnotificationhubs.application.port.in.streaming;

public interface ITransactionListenerPort<T> {
    void transactionReceivedEvent(T transactionReceivedEvent);
}
