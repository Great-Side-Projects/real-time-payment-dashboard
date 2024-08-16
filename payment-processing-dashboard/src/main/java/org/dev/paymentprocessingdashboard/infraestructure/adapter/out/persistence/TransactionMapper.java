package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.*;
import org.dev.paymentprocessingdashboard.domain.event.EventType;
import org.dev.paymentprocessingdashboard.domain.event.TransactionProcessedEvent;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public static TotalTransactionSummary toTotalTransactionSummary(TransactionSummary transactionSummary) {
        return new TotalTransactionSummary(
                transactionSummary.getTotalCount(),
                transactionSummary.getTotalValue());
    }

    public static TransactionProcessedEvent toTransactionProcessedEvent(List<Transaction> transaction){
        return new TransactionProcessedEvent(UUID.randomUUID().toString(),new Date(), EventType.PROCESSED, transaction);
    }
}
