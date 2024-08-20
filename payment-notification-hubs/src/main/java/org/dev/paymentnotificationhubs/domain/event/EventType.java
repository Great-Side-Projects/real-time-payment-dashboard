package org.dev.paymentnotificationhubs.domain.event;

public enum EventType {
    RECEIVED,
    FAILED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
