package org.dev.paymentprocessingdashboard.domain.event;

import org.dev.paymentprocessingdashboard.domain.Transaction;

import java.util.Date;
import java.util.List;

public class TransactionProcessedEvent extends Event<List<Transaction>>{

    public TransactionProcessedEvent(String id, Date date, EventType type, List<Transaction> data) {
        this.setId(id);
        this.setDate(date);
        this.setType(type);
        this.setData(data);
    }

    public TransactionProcessedEvent() {
    }
}
