package org.dev.paymentprocessingdashboard.domain;

public interface TransactionPerMinuteSummaryProjection {
    String getMinute();
    long getTotalTransactions();
}
