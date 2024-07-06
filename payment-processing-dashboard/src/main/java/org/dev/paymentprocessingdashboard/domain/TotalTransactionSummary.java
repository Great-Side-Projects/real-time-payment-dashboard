package org.dev.paymentprocessingdashboard.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class TotalTransactionSummary {

    private List<TransactionSummaryProjection> transactionSumaries;
    private double totalProcessedAmount;
    private long totalSuccessTransactions;
    private long totalFailedTransactions;
    private String totalTransactionsPerUser;


    public TotalTransactionSummary(List<TransactionSummaryProjection> transactionSumaries) {
        BuildTotalTransactionSumary(transactionSumaries);
    }

    private void BuildTotalTransactionSumary( List<TransactionSummaryProjection> transactionSumaries) {
        this.totalProcessedAmount = transactionSumaries.stream().mapToDouble(TransactionSummaryProjection::getTotalAmount).sum();
        this.totalSuccessTransactions = transactionSumaries.stream().mapToLong(TransactionSummaryProjection::getTotalSuccess).sum();
        this.totalFailedTransactions = transactionSumaries.stream().mapToLong(TransactionSummaryProjection::getTotalFailed).sum();
        this.totalTransactionsPerUser = transactionSumaries.stream()
                .map(transactionSumary -> "User " + transactionSumary.getUser() + ": Total transactions: " + transactionSumary.getTotalTransactions())
                .reduce((s1, s2) -> s1 + ", " + s2).orElse("No transactions");
    }

    @Override
    public String toString() {
        return "*** Total Transaction Summary *** " + "\n" +
                "Total processed amount: $" + this.totalProcessedAmount + "\n" +
                "Number of successful transactions: " + this.totalSuccessTransactions + "\n" +
                "Number of failed transactions: " + this.totalFailedTransactions + "\n" +
                "Number of transactions per user: " + this.totalTransactionsPerUser +
                "\n---------------------------------";
    }

}
