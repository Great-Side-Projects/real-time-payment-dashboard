package org.dev.paymentwebsocketnotification.infraestructure.adapter.out;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentwebsocketnotification.application.port.out.ITransactionEventTemplatePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionRabbitMQTemplateAdapter<T> implements ITransactionEventTemplatePort<T> {
    private RabbitTemplate rabbitTemplate;

    public TransactionRabbitMQTemplateAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @CircuitBreaker(name = "transactionRabbitMQEvent", fallbackMethod = "fallbackTransactionRabbitMQEvent")
    public void send(String routingKey, T data) {
        try {
            rabbitTemplate.convertAndSend(routingKey, data);
        } catch (Exception e) {
            System.out.println("Error sending transaction RabbitMQ event: " + e.getMessage());
            throw new RuntimeException("Error sending transaction event" + e.getMessage());
        }
    }

    public void fallbackTransactionRabbitMQEvent(String routingKey, T data, Throwable t) {
        //Implamentation of fallback method for send method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error sending transaction RabbitMQ event: " + t.getMessage());
    }
}
