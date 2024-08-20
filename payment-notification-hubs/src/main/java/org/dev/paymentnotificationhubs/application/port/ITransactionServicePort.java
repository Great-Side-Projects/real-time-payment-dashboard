package org.dev.paymentnotificationhubs.application.port;

import org.dev.paymentnotificationhubs.domain.event.Event;
import org.dev.paymentnotificationhubs.domain.event.TransactionReceivedEvent;

public interface ITransactionServicePort {
    void transactionReceivedEvent(Event<TransactionReceivedEvent> transactionReceivedEvent);
}
