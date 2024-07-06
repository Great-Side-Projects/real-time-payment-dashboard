package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummary;
import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummaryProjection;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TransactionMapper {
    public static TransactionEntity toTransactionEntity(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.fromString(transaction.getId()));
        transactionEntity.setUserid(transaction.getUserid());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setStatus(transaction.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(transaction.getTimestamp(), formatter);
        transactionEntity.setTimestamp(zonedDateTime.toLocalDateTime());
        transactionEntity.setLocation(transaction.getLocation());
        return transactionEntity;
    }

    public static Transaction toTransaction(TransactionEntity transactionEntity) {
        Transaction transaction = new Transaction(
                transactionEntity.getId().toString(),
                transactionEntity.getUserid(),
                transactionEntity.getAmount(),
                transactionEntity.getStatus(),
                transactionEntity.getTimestamp().toString(),
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
}
