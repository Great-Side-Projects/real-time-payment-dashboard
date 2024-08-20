package org.dev.paymentprocessing.domain.event;

import org.dev.paymentprocessing.domain.Transaction;
import java.util.Date;
import java.util.List;

public class TransactionReceivedEvent extends Event<List<Transaction>> {

    public TransactionReceivedEvent(String id, Date date, EventType type, List<Transaction> data) {
        this.setId(id);
        this.setDate(date);
        this.setType(type);
        this.setData(data);
    }

    public TransactionReceivedEvent() {
    }
}
