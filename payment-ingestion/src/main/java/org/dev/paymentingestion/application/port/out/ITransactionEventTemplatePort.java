package org.dev.paymentingestion.application.port.out;

import org.springframework.lang.Nullable;

public interface ITransactionEventTemplatePort<T> {

    void send(@Nullable T data);
}
