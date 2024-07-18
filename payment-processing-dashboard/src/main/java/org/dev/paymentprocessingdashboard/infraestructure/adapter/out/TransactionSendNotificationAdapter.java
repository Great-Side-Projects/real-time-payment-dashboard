package org.dev.paymentprocessingdashboard.infraestructure.adapter.out;

import org.dev.paymentprocessingdashboard.application.port.INotificationStrategy;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.application.notificationstrategy.FailureNotificationStrategy;
import org.dev.paymentprocessingdashboard.application.notificationstrategy.HighAmountNotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionSendNotificationAdapter implements ITransactionSendNotificationPort {

    private final ITransactionEventTemplatePort<String> transactionWebSocketAdapter;

    public TransactionSendNotificationAdapter(ITransactionEventTemplatePort<String> transactionWebSocketAdapter) {
        this.transactionWebSocketAdapter = transactionWebSocketAdapter;
    }

    @Override
    public List<Transaction> send(List<Transaction> transactions) {

        List<INotificationStrategy> strategies = List.of(new FailureNotificationStrategy(), new HighAmountNotificationStrategy());
        List<Transaction> notifiedTransactions = new ArrayList<>();
        transactions.forEach(transaction -> strategies.stream()
                .filter(strategy -> strategy.applies(transaction))
                .findFirst()
                .ifPresent(strategy -> {
                    String message = strategy.getMessage(transaction);
                    transactionWebSocketAdapter.send(message);
                    notifiedTransactions.add(transaction);
                }));
        return notifiedTransactions;
    }
}
