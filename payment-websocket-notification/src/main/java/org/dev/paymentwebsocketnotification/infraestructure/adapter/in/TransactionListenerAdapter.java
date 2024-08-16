package org.dev.paymentwebsocketnotification.infraestructure.adapter.in;

import org.dev.paymentwebsocketnotification.application.port.in.ITransactionListenerPort;
import org.dev.paymentwebsocketnotification.application.service.TransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TransactionListenerAdapter implements ITransactionListenerPort<String> {

    Logger logger = Logger.getLogger(getClass().getName());
    private final TransactionService transactionService;

    public TransactionListenerAdapter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.websocket}")
    public void TransactionWebsocketNotification(String transactionNotified) {

        transactionService.sendTransactionWebsocketNotification(transactionNotified);
        logger.log(Level.INFO, "Transaction Notified: {0}", new Object[]{transactionNotified});
    }
}
