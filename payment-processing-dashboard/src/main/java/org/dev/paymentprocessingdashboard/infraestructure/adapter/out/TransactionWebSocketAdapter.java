package org.dev.paymentprocessingdashboard.infraestructure.adapter.out;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class  TransactionWebSocketAdapter implements ITransactionEventTemplatePort<String> {

    private final SimpMessagingTemplate messagingTemplate;

    @Value("${spring.websocket.destination}")
    private String DESTINATION;

    public TransactionWebSocketAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void send(String data) {
        messagingTemplate.convertAndSend(DESTINATION, data);
    }
}
