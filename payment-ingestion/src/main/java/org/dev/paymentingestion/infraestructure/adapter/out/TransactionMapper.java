package org.dev.paymentingestion.infraestructure.adapter.out;


import org.dev.paymentingestion.domain.Transaction;
import org.dev.paymentingestion.domain.event.EventType;
import org.dev.paymentingestion.domain.event.TransactionReceivedEvent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransactionMapper {

    public static TransactionReceivedEvent toTransactionProcessedEvent(List<Transaction> transaction){
        return new TransactionReceivedEvent(UUID.randomUUID().toString(),new Date(), EventType.RECEIVED, transaction);
    }
}
