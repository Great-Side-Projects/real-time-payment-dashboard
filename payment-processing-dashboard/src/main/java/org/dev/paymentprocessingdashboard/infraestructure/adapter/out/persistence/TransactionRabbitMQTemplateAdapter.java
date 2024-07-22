package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "transactionRabbitMQEventStreaming", fallbackMethod = "fallbackTransactionRabbitMQEvent")
    public void send(String[] data) {
        try {
            rabbitTemplate.convertAndSend(QUEUE_NAME, data);
        } catch (Exception e) {
            System.out.println("Error sending transaction RabbitMQ event: " + e.getMessage());
            throw new RuntimeException("Error sending transaction event" + e.getMessage());
        }
    }

    public void fallbackTransactionRabbitMQEvent(String[] data, Throwable t) {
        //Implamentation of fallback method for send method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error sending transaction RabbitMQ event: " + t.getMessage());
    }
}
