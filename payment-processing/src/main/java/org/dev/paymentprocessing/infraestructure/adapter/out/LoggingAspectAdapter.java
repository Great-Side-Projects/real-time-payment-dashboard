package org.dev.paymentprocessing.infraestructure.adapter.out;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.dev.paymentprocessing.application.port.out.ILoggingAspectPort;
import org.dev.paymentprocessing.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessing.domain.Transaction;
import org.dev.paymentprocessing.domain.event.TransactionReceivedEvent;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LoggingAspectAdapter implements ILoggingAspectPort {

    private final ITransactionEventTemplatePort<String[]> transactionRabbitMQTemplateAdapter;
    private final String SEPARATOR = "::";
    private final int CHUNK_MESSAGE_SIZE = 5000;

    public LoggingAspectAdapter(ITransactionEventTemplatePort<String[]> transactionRabbitMQTemplateAdapter) {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
    }

    @After("execution(* org.dev.paymentprocessing.application.service.TransactionService.filterTransactions(..)) &&  args(status, userId, minAmount, maxAmount, transactionId, nextPagingState, size)")
    @Override
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {
        String action = "Filter Transactions";
        String details = String.format("Filter applied: {Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %s, Size: %d}",
                status, userId, minAmount, maxAmount, transactionId, nextPagingState, size);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);

        transactionRabbitMQTemplateAdapter.send(new String[]{actionLogMessage});
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessing.application.service.TransactionService.processTransaction(..)))", returning = "transactionReceivedEvent")
    @Override
    public void logProcessTransaction(TransactionReceivedEvent transactionReceivedEvent) {
        if (transactionReceivedEvent == null) {
            return;
        }

        List<String> logMessages = new ArrayList<>();
        transactionReceivedEvent.getData().forEach(transaction -> {
            String action = "Process Transaction";
            String details = String.format("Processed transaction: EventId: %s %s ", transactionReceivedEvent.getId(), transaction.toString());

            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            logMessages.add(actionLogMessage);
            if (logMessages.size() == CHUNK_MESSAGE_SIZE) {
                transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
                logMessages.clear();
            }
        });

        if (logMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
    }
}
