package org.dev.paymentwebsocketnotification.infraestructure.adapter.out;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.dev.paymentwebsocketnotification.application.port.out.ILoggingAspectPort;
import org.dev.paymentwebsocketnotification.application.port.out.ITransactionEventTemplatePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LoggingAspectAdapter implements ILoggingAspectPort {

    private final ITransactionEventTemplatePort<String[]> transactionRabbitMQTemplateAdapter;
    private final String SEPARATOR = "::";

    @Value("${spring.rabbitmq.queue.log}")
    private String QUEUE_NAME;

    public LoggingAspectAdapter(ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter) {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
    }

    @Override
    @AfterReturning(value = "execution(* org.dev.paymentwebsocketnotification.application.service.TransactionService.sendTransactionWebsocketNotification(..))", returning = "transactionNotified")
    public void logNotification(String transactionNotified) {
        if (transactionNotified.isEmpty())
            return;

        String action = "Send Notification";
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, transactionNotified);
        transactionRabbitMQTemplateAdapter.send(QUEUE_NAME, new String[]{actionLogMessage});
    }
}
