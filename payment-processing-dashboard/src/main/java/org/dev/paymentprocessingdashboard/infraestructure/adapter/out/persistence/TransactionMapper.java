package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.*;

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

    public static LogEntity toLogEntity(Log actionLog) {
        LogEntity log = new LogEntity();
        log.setAction(actionLog.getAction());
        log.setDetails(actionLog.getDetails());
        return log;
    }

    public static TotalTransactionSummary toTotalTransactionSummary(TransactionSummary transactionSummary) {
        return new TotalTransactionSummary(
                transactionSummary.getTotalCount(),
                transactionSummary.getTotalValue());
    }

}
