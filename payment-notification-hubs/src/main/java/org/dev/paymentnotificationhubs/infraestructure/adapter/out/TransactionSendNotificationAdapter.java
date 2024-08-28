package org.dev.paymentnotificationhubs.infraestructure.adapter.out;

import org.dev.paymentnotificationhubs.application.INotificationStrategy;
import org.dev.paymentnotificationhubs.application.notificationstrategy.FailureNotificationStrategy;
import org.dev.paymentnotificationhubs.application.notificationstrategy.HighAmountNotificationStrategy;
import org.dev.paymentnotificationhubs.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentnotificationhubs.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentnotificationhubs.domain.Transaction;
import org.dev.paymentnotificationhubs.infraestructure.NotificationQueueProps;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionSendNotificationAdapter implements ITransactionSendNotificationPort {

    private final ITransactionEventTemplatePort<String> transactionRabbitMQTemplateAdapter;
    private final NotificationQueueProps notificationQueueProps;

    public TransactionSendNotificationAdapter(ITransactionEventTemplatePort<String> transactionRabbitMQTemplateAdapter,
                                              NotificationQueueProps notificationQueueProps) {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
        this.notificationQueueProps = notificationQueueProps;
    }

    @Override
    public void send(List<Transaction> transactions, String eventId) {

        List<INotificationStrategy> strategies = List.of(new FailureNotificationStrategy(), new HighAmountNotificationStrategy());
        transactions.forEach(transaction -> strategies.stream()
                .filter(strategy -> strategy.applies(transaction))
                .findFirst()
                .ifPresent(strategy -> {
                    String message =  String.format(strategy.getMessage(transaction), eventId);;
                    notificationQueueProps.getNotificationQueues().stream()
                            .filter(queue -> queue.isEnabled())
                            .forEach(queue -> transactionRabbitMQTemplateAdapter.send(queue.getName(), message));
                }));
    }
}
