package org.dev.paymentwebsocketnotification.domain;

public enum TransactionStatusEnum {
    SUCCESS,
    FAILURE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
