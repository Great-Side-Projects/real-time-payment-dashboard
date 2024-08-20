package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessing.domain.*;
import org.dev.paymentprocessing.domain.event.EventType;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransactionMapper {
    public static TransactionEntity toTransactionEntity(Transaction transaction, String eventId) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setEventId(eventId);
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

    public static TransactionReceivedEvent toTransactionProcessedEvent(List<Transaction> transaction){
        return new TransactionReceivedEvent(UUID.randomUUID().toString(),new Date(), EventType.PROCESSED, transaction);
    }
}
