package org.dev.paymentprocessingdashboard.application.port.out;

import lombok.Getter;
import org.dev.paymentprocessingdashboard.domain.Transaction;


import java.util.List;
@Getter
public class TransactionFilterResponse {
    List<Transaction> transactions;
    String nextPagingState;
    boolean hasNext;
    int numberOfElements;

    public TransactionFilterResponse(List<Transaction> transactions, String nextPagingState, boolean hasNext, int numberOfElements) {
        this.transactions = transactions;
        this.nextPagingState = nextPagingState;
        this.hasNext = hasNext;
        this.numberOfElements = numberOfElements;
     }
}
