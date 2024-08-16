package org.dev.paymentwebsocketnotification.application.port.out;

import org.springframework.lang.Nullable;

public interface ITransactionEventTemplatePort<T> {
    void send(String routingKey ,@Nullable T data);
}
