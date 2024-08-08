package org.dev.paymentprocessingdashboard.application.port.in;

import org.dev.paymentprocessingdashboard.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.nio.ByteBuffer;
import java.util.List;

public interface ITransactionServicePort {
    TransactionFilterResponse filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size);
    List<Transaction> processTransaction(List<Transaction> transactions);
    TotalTransactionSummary totalTransactionSummary();
    TotalTransactionPerMinuteSummary summaryTransactionsPerMinute();
}
