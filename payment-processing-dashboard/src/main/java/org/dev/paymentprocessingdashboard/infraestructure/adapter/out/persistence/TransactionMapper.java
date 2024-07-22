package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.ActionLog;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummaryProjection;

import java.time.LocalDateTime;
public class TransactionMapper {
    public static TransactionEntity toTransactionEntity(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transaction.getId());
        transactionEntity.setUserid(transaction.getUserId());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setStatus(transaction.getStatus());
        transactionEntity.setTime(transaction.getTime());
        transactionEntity.setLocation(transaction.getLocation());
        return transactionEntity;
    }

    public static Transaction toTransaction(TransactionEntity transactionEntity) {
        Transaction transaction = new Transaction(
                transactionEntity.getId().toString(),
                transactionEntity.getUserid(),
                transactionEntity.getAmount(),
                transactionEntity.getStatus(),
                transactionEntity.getTime().toString(),
                transactionEntity.getLocation()
        );
        return transaction;
    }

    public static TransactionPerMinuteSummary toTransactionPerMinuteSummary(TransactionPerMinuteSummaryProjection transactionPerMinuteSummaryProjection) {
        return new TransactionPerMinuteSummary(
                transactionPerMinuteSummaryProjection.getMinute(),
                transactionPerMinuteSummaryProjection.getTotalTransactions()
        );
    }
    public static ActionLogEntity toActionLogEntity(ActionLog actionLog) {
        ActionLogEntity log = new ActionLogEntity();
        log.setAction(actionLog.getAction());
        log.setDetails(actionLog.getDetails());
        log.setTimestamp(LocalDateTime.now());
        return log;
    }
}
