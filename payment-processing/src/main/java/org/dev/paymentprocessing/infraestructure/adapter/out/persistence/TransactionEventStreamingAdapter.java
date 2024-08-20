package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentprocessing.application.port.out.ITransactionEventStreamingPort;
import org.dev.paymentprocessing.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessing.common.PersistenceAdapter;
import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.event.Event;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@PersistenceAdapter
public class TransactionEventStreamingAdapter implements ITransactionEventStreamingPort<List<Transaction>> {

    private final ITransactionEventTemplatePort<Event<List<Transaction>>> transactionEventKafkaTemplateAdapter;
    private static final String MESSAGE_ERROR = "Error sending event to RabbitMQ {0}";

    Logger logger = Logger.getLogger(getClass().getName());

    public TransactionEventStreamingAdapter(
            ITransactionEventTemplatePort<Event<List<Transaction>>> transactionEventKafkaTemplateAdapter) {
        this.transactionEventKafkaTemplateAdapter = transactionEventKafkaTemplateAdapter;
    }

    @CircuitBreaker(name = "transactionEventStreaming", fallbackMethod = "fallbackProcessedEvent")
    public void sendProcessedEvent(List<Transaction> transaction) {
        TransactionReceivedEvent processed = TransactionMapper.toTransactionProcessedEvent(transaction);
        transactionEventKafkaTemplateAdapter.send(processed);
    }

    public void fallbackProcessedEvent(List<Transaction> transaction, Throwable t) {
        logger.log(Level.SEVERE, "Error sending processed event: {0}", t.getMessage());
        try {
            TransactionReceivedEvent processed = TransactionMapper.toTransactionProcessedEvent(transaction);
            String eventString = String.format("Error sending processed event: %s, Event: %s", t.getMessage(), processed);
            //TODO:// whay happens if the event is not sent? delete the transaction?
            // Handle the error
        } catch (Exception e) {
            logger.log(Level.SEVERE, MESSAGE_ERROR, e.getMessage());
        }
    }
}

