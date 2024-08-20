package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessing.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessing.domain.event.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventKafkaTemplateAdapter<T> implements ITransactionEventTemplatePort<Event<T>> {

    private final KafkaTemplate<String, Event<?>> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    public TransactionEventKafkaTemplateAdapter(KafkaTemplate<String, Event<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Event<T> event) {

        kafkaTemplate.send(TOPIC_NAME, event.getId(), event);
    }
}
