package org.dev.paymentprocessingdashboard.application;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.dev.paymentprocessingdashboard.application.port.ITransactionConvertProviderPort;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.domain.ActionLog;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    private final ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter;
    private final IActionLogPersistencePort actionLogPersistenceAdapter;
    private final ITransactionConvertProviderPort transactionConvertProviderAdapter;
    private final String SEPARATOR = "::";
    private final int CHUNK = 1000;
    private final String ANSIRED = "\u001B[31m";
    private final String ANSIYELLOW = "\u001B[33m";


    public LoggingAspect(ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter, IActionLogPersistencePort actionLogPersistenceAdapter, ITransactionConvertProviderPort transactionConvertProviderAdapter) {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
        this.actionLogPersistenceAdapter = actionLogPersistenceAdapter;
        this.transactionConvertProviderAdapter = transactionConvertProviderAdapter;
    }

    @After("execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.filterTransactions(..)) && args(status, userId, minAmount, maxAmount, transactionId, page, size)")
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size) {
        String action = "Filter Transactions";
        String details = String.format("Filter applied - Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %d, Size: %d",
                status, userId, minAmount, maxAmount, transactionId, page, size);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);

        transactionRabbitMQTemplateAdapter.send(new String[]{actionLogMessage});
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.application.service.LogReaderService.readLogFile(..)))", returning = "transactions")
    public void logProcessTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }

        List<String> actionLogMessages = new ArrayList<>();
        transactions.forEach(transaction -> {
            String action = "Process Transaction";
            String details = String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Time: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTimestamp(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            actionLogMessages.add(actionLogMessage);
            if (actionLogMessages.size() == CHUNK)
            {
                transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
                actionLogMessages.clear();
            }
        });

        if (actionLogMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.infraestructure.adapter.out.TransactionSendNotificationAdapter.send(..))", returning = "notifiedTransactions")
    public void logNotification(List<Transaction> notifiedTransactions) {

        if (notifiedTransactions.isEmpty()) {
            return;
        }

        List<String> actionLogMessages = new ArrayList<>();
        notifiedTransactions.forEach(transaction -> {
            String action = "Send Notification";
            // clean ANSI color red and

            String details = String.format("Send notification - Id: %s, UserId: %s, Amount: %s, Status: %s, Time: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTimestamp(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            actionLogMessages.add(actionLogMessage);
            if (actionLogMessages.size() == CHUNK)
            {
                transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
                actionLogMessages.clear();
            }
        });

        if (actionLogMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void actionLogEvent(String[] data) {

        List<ActionLog> actionLogs = new ArrayList<>();
        for (String actionLogMessage : data) {
            String[] actionLogData = actionLogMessage.split(SEPARATOR);
            ActionLog actionLog = new ActionLog(actionLogData[0], actionLogData[1]);
            actionLogs.add(actionLog);
        }

            if (actionLogs.size() == 1)
                actionLogPersistenceAdapter.save(actionLogs.get(0));
            else
                actionLogPersistenceAdapter.saveAll(actionLogs);

        actionLogs.forEach(actionLog -> {
            System.out.println(" [x] Received '" + actionLog + "'");
        });
    }
}
