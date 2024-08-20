package org.dev.paymentprocessing.application.port.in.stream;

public interface ITransactionListenerPort<T> {
    void transactionReceivedEvent(T transactionReceivedEvent);
}
