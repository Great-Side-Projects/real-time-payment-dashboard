package org.dev.paymentprocessing.application.port.out;

public interface ITransactionEventStreamingPort<T> {

    void sendProcessedEvent(T transactions);
}
