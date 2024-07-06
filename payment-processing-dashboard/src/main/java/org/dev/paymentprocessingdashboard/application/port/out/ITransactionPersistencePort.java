package org.dev.paymentprocessingdashboard.application.port.out;


import org.dev.paymentprocessingdashboard.domain.TotalTransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TotalTransactionSummary;
import org.dev.paymentprocessingdashboard.domain.Transaction;

import java.util.List;

public interface ITransactionPersistencePort {
    Transaction save(Transaction transaction);
    void saveAll(List<Transaction> transactions);
    TotalTransactionSummary TotalTransactionSummary();
    TotalTransactionPerMinuteSummary SummaryTransactionsPerMinute();
}
