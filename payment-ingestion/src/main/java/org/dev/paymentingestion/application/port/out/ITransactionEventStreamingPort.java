package org.dev.paymentingestion.application.port.out;

public interface ITransactionEventStreamingPort<T> {
    void sendReceivedEvent(T transactions);
}
