package org.dev.paymentprocessingdashboard.domain;

import lombok.Getter;

@Getter
public class TransactionPerMinuteSummary {
    private final String minute;
    private final long totalTransactions;

    public TransactionPerMinuteSummary(String minute, long totalTransactions) {
        this.minute = minute;
        this.totalTransactions = totalTransactions;
    }
}
