package org.dev.paymentingestion.infraestructure.adapter.out;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentingestion.application.port.out.ITransactionEventStreamingPort;
import org.dev.paymentingestion.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentingestion.common.PersistenceAdapter;
import org.dev.paymentingestion.domain.Transaction;
import org.dev.paymentingestion.domain.event.Event;
import org.dev.paymentingestion.domain.event.TransactionReceivedEvent;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@PersistenceAdapter
public class TransactionEventStreamingAdapter implements ITransactionEventStreamingPort<List<Transaction>> {

    private final ITransactionEventTemplatePort<Event<List<Transaction>>> transactionEventKafkaTemplateAdapter;
    private static final String MESSAGE_ERROR = "Error sending event to RabbitMQ {0}";
    private final int CHUNK_SIZE = 1000;

    Logger logger = Logger.getLogger(getClass().getName());

    public TransactionEventStreamingAdapter(
            ITransactionEventTemplatePort<Event<List<Transaction>>> transactionEventKafkaTemplateAdapter) {
        this.transactionEventKafkaTemplateAdapter = transactionEventKafkaTemplateAdapter;
    }

    @CircuitBreaker(name = "transactionEventStreaming", fallbackMethod = "fallbackReceivedEvent")
    public void sendReceivedEvent(List<Transaction> transaction) {

        if (transaction.size() < CHUNK_SIZE) {
            TransactionReceivedEvent received = TransactionMapper.toTransactionProcessedEvent(transaction);
            transactionEventKafkaTemplateAdapter.send(received);
            return;
        }

        for (int i = 0; i < transaction.size(); i += CHUNK_SIZE) {
            List<Transaction> chunk = transaction.subList(i, Math.min(i + CHUNK_SIZE, transaction.size()));
            TransactionReceivedEvent processed = TransactionMapper.toTransactionProcessedEvent(chunk);
            transactionEventKafkaTemplateAdapter.send(processed);
        }
    }

    public void fallbackReceivedEvent(List<Transaction> transaction, Throwable t) {
        logger.log(Level.SEVERE, "Error sending received event: {0}", t.getMessage());
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

