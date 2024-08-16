package org.dev.paymentprocessingdashboard.application.port.out;

public interface ITransactionEventStreamingPort<T> {

    void sendProcessedEvent(T transactions);
}
