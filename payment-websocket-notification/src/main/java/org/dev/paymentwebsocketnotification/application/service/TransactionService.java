package org.dev.paymentwebsocketnotification.application.service;

import org.dev.paymentwebsocketnotification.application.port.ITransactionServicePort;
import org.dev.paymentwebsocketnotification.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentwebsocketnotification.common.UseCase;
import org.springframework.beans.factory.annotation.Value;

@UseCase
public class TransactionService implements ITransactionServicePort {

    @Value("${spring.websocket.destination}")
    private String DESTINATION;
    private final ITransactionEventTemplatePort<String> transactionWebSocketAdapter;

    public TransactionService(ITransactionEventTemplatePort<String> transactionWebSocketAdapter) {
        this.transactionWebSocketAdapter = transactionWebSocketAdapter;
    }

    public String sendTransactionWebsocketNotification(String data) {
        // send notification hub to email, sms, push notification, etc
        transactionWebSocketAdapter.send(DESTINATION, data);
        return data;
    }
}
