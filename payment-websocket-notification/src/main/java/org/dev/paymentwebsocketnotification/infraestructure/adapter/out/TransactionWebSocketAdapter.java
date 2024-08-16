package org.dev.paymentwebsocketnotification.infraestructure.adapter.out;

import org.dev.paymentwebsocketnotification.application.port.out.ITransactionEventTemplatePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionWebSocketAdapter<T> implements ITransactionEventTemplatePort<T> {

    private final SimpMessagingTemplate messagingTemplate;

    public TransactionWebSocketAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void send(String routingKey, T data) {
        messagingTemplate.convertAndSend(routingKey, data);
    }
}
