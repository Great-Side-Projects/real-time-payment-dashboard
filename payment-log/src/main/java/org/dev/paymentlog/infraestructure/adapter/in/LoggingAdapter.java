package org.dev.paymentlog.infraestructure.adapter.in;

import org.dev.paymentlog.application.port.ITransactionServicePort;
import org.dev.paymentlog.application.port.in.ILoggingEventPort;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingAdapter implements ILoggingEventPort {

    private final ITransactionServicePort transactionService;
    public LoggingAdapter(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    @Override
    public void LogEvent(String[] data) {
        transactionService.LogEvent(data);
    }
}
