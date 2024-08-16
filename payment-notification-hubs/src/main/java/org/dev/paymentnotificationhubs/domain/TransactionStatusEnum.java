package org.dev.paymentnotificationhubs.domain;

public enum TransactionStatusEnum {
    SUCCESS,
    FAILURE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
