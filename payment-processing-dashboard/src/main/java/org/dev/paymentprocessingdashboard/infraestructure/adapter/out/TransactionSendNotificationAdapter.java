package org.dev.paymentprocessingdashboard.infraestructure.adapter.out;

import org.dev.paymentprocessingdashboard.application.port.ITransactionBusinessRulePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionSendNotificationPort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.domain.TransactionStatusEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionSendNotificationAdapter implements ITransactionSendNotificationPort, ITransactionBusinessRulePort {

    private final ITransactionEventTemplatePort<String> transactionWebSocketAdapter;
    private final String ANSIRED = "\u001B[31m";
    private final String ANSIYELLOW = "\u001B[33m";
    private final int AMOUNT_1000 = 1000;


    public TransactionSendNotificationAdapter(ITransactionEventTemplatePort<String> transactionWebSocketAdapter) {
        this.transactionWebSocketAdapter = transactionWebSocketAdapter;
    }

    @Override
    public void send(List<Transaction> transactions) {

        transactions.forEach(transaction -> {
            boolean hasNotificationRule = false;
            String transactionMessage = transaction.toString();

            if (isFailureTransaction(transaction)) {
                hasNotificationRule = true;
                transactionMessage = colorizeMessage(transactionMessage, ANSIRED);
            } else if (isHighAmountTransaction(transaction)) {
                hasNotificationRule = true;
                transactionMessage = colorizeMessage(transactionMessage, ANSIYELLOW);
            }

            if (hasNotificationRule) {
                transactionWebSocketAdapter.send(transactionMessage);
            }
        });
    }
    @Override
    public boolean isFailureTransaction(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatusEnum.FAILURE.toString());
    }
    @Override
    public boolean isHighAmountTransaction(Transaction transaction) {
        return transaction.getAmount() > AMOUNT_1000;
    }

    private String colorizeMessage(String message, String color) {
        return color + message;
    }
}
