package org.dev.paymentnotificationhubs.domain.event;

import org.dev.paymentnotificationhubs.domain.Transaction;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransactionReceivedEvent extends Event<List<Transaction>> implements Serializable {

    public TransactionReceivedEvent(String id, Date date, EventType type, List<Transaction> data) {
        this.setId(id);
        this.setDate(date);
        this.setType(type);
        this.setData(data);
    }

    public TransactionReceivedEvent() {
    }
}
