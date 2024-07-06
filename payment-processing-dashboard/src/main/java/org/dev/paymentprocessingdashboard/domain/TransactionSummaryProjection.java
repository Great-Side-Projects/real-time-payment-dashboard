package org.dev.paymentprocessingdashboard.domain;

public interface TransactionSummaryProjection {

    double getTotalAmount();
    long getTotalSuccess();
    long getTotalFailed();
    String getUser();
    long getTotalTransactions();
}

