package org.dev.paymentprocessing.infraestructure.adapter.in.stream;

import org.dev.paymentprocessing.application.port.in.stream.ITransactionListenerPort;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;
import org.dev.paymentprocessing.application.port.ITransactionServicePort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TransactionListenerAdapter implements ITransactionListenerPort<TransactionReceivedEvent> {

    Logger logger = Logger.getLogger(getClass().getName());
    private final ITransactionServicePort transactionService;

    public TransactionListenerAdapter(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void transactionReceivedEvent(TransactionReceivedEvent transactionReceivedEvent){

        transactionService.processTransaction(transactionReceivedEvent);
        logger.log(Level.INFO, "Transaction processed: EventId {0} - {1}", new Object[]{transactionReceivedEvent.getId(), transactionReceivedEvent.getData()});
    }
}
