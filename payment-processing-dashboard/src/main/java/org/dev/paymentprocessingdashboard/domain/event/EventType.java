package org.dev.paymentprocessingdashboard.domain.event;

public enum EventType {
    PROCESSED,
    FAILED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
