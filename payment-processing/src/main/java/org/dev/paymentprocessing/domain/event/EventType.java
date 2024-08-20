package org.dev.paymentprocessing.domain.event;

public enum EventType {
    RECEIVED,
    PROCESSED,
    FAILED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
