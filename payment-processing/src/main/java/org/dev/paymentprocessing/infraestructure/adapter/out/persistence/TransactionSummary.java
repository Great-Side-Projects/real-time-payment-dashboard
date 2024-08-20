package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import lombok.Getter;

@Getter
public class TransactionSummary {
    private long totalCount;
    private double totalValue;

    public TransactionSummary(long totalCount, double totalValue) {
        this.totalCount = totalCount;
        this.totalValue = totalValue;
    }
}
