package org.dev.paymentlog.application.port;

public interface ITransactionServicePort {
    void LogEvent(String[] data);
}
