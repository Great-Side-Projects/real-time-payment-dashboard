package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionRabbitMQTemplateAdapter implements ITransactionEventTemplatePort<String[]> {
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.name}")
    private String QUEUE_NAME;

    public TransactionRabbitMQTemplateAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(String[] data) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, data);
    }
}
