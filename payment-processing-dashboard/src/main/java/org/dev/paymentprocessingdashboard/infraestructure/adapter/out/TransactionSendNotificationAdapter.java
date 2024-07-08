package org.dev.paymentprocessingdashboard.infraestructure.adapter.out;

import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionSendNotificationAdapter implements ITransactionSendNotificationPort {

    private final ITransactionEventTemplatePort<String> transactionWebSocketAdapter;
    private final String ANSIRED = "\u001B[31m";
    private final String ANSIYELLOW = "\u001B[33m";


    public TransactionSendNotificationAdapter(ITransactionEventTemplatePort<String> transactionWebSocketAdapter) {
        this.transactionWebSocketAdapter = transactionWebSocketAdapter;
    }

    @Override
    public void send(List<Transaction> transactions) {

        transactions.forEach(transaction -> {
            String transactionMessage = transaction.toString();
            if (transaction.getStatus().equals("failure")) {
                transactionMessage = ANSIRED+transactionMessage;
            }
            else if (transaction.getAmount() > 1000) {
                transactionMessage = ANSIYELLOW + transactionMessage;
            }
            transactionWebSocketAdapter.send(transactionMessage);
        });
    }
}
