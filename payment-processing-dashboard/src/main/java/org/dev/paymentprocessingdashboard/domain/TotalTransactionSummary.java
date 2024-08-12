package org.dev.paymentprocessingdashboard.domain;

import lombok.Getter;

@Getter
public class TotalTransactionSummary {

    private long totalCount;
    private double totalValue;

    public TotalTransactionSummary(long totalCount, double totalValue) {
        this.totalCount = totalCount;
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "*** Total Transaction Summary *** " + "\n" +
                "Total processed value: $" + String.format("%.2f", this.totalValue) + "\n" +
                "Number of transactions: " + this.totalCount + "\n" +
                "\n---------------------------------";
    }

}
