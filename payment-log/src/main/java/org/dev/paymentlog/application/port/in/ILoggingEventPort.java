package org.dev.paymentlog.application.port.in;

public interface ILoggingEventPort {
    void LogEvent(String[] data);
}
