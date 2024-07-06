package org.dev.paymentprocessingdashboard.domain;

import java.util.List;

public class TotalTransactionPerMinuteSummary {
    private List<TransactionPerMinuteSummary> transactionPerMinuteSummaries;

    public TotalTransactionPerMinuteSummary(List<TransactionPerMinuteSummary> transactionPerMinuteSummaries) {
        this.transactionPerMinuteSummaries = transactionPerMinuteSummaries;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*** Total Transactions per minute ***\n");
        for (TransactionPerMinuteSummary transactionPerMinuteSummary : transactionPerMinuteSummaries) {
            stringBuilder.append(transactionPerMinuteSummary.getMinute()).append(" : ").append(transactionPerMinuteSummary.getTotalTransactions()).append("\n");
        }
        stringBuilder.append("---------------------------------");
        return stringBuilder.toString();

    }
}
