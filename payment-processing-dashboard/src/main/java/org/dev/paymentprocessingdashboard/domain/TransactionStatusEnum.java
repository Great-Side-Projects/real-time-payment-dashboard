package org.dev.paymentprocessingdashboard.domain;

public enum TransactionStatusEnum {
    SUCCESS,
    FAILURE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
