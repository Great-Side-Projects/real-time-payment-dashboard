package org.dev.paymentnotificationhubs.infraestructure.adapter.in.stream;

import org.dev.paymentnotificationhubs.application.port.in.streaming.ITransactionListenerPort;
import org.dev.paymentnotificationhubs.application.service.TransactionService;
import org.dev.paymentnotificationhubs.domain.event.Event;
import org.dev.paymentnotificationhubs.domain.event.TransactionReceivedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TransactionListenerAdapter implements ITransactionListenerPort<TransactionReceivedEvent> {

    Logger logger = Logger.getLogger(getClass().getName());
    private final TransactionService transactionService;

    public TransactionListenerAdapter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void transactionReceivedEvent(TransactionReceivedEvent transactionReceivedEvent){

        transactionService.transactionReceivedEvent(transactionReceivedEvent);
        logger.log(Level.INFO, "Transaction hub sent: EventId {0} - {1}", new Object[]{transactionReceivedEvent.getId(),transactionReceivedEvent.getData()});
    }
}
