package org.dev.paymentprocessing.application.port;

import org.dev.paymentprocessing.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessing.domain.TotalTransactionSummary;
import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.event.Event;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;

import java.util.List;

public interface ITransactionServicePort {
    TransactionFilterResponse filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    List<Transaction> processTransaction(Event<TransactionReceivedEvent> transactionReceivedEvent);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionSummary getTransactionSummaryByStatus(String status);
    TotalTransactionSummary getTransactionSummaryByUserId(String userId);
}
