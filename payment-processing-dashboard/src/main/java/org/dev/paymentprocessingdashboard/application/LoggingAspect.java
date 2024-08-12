package org.dev.paymentprocessingdashboard.application;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.dev.paymentprocessingdashboard.application.port.ITransactionConvertProviderPort;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.domain.Log;
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
    private final int CHUNK_MESSAGE_SIZE = 5000;

    public LoggingAspect(ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter,
                         IActionLogPersistencePort actionLogPersistenceAdapter,
                         ITransactionConvertProviderPort transactionConvertProviderAdapter)
    {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
        this.actionLogPersistenceAdapter = actionLogPersistenceAdapter;
        this.transactionConvertProviderAdapter = transactionConvertProviderAdapter;
    }

    @After("execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.filterTransactions(..)) &&  args(status, userId, minAmount, maxAmount, transactionId, nextPagingState, size)")
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {
        String action = "Filter Transactions";
        String details = String.format("Filter applied - Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %d, Size: %d",
                status, userId, minAmount, maxAmount, transactionId, nextPagingState, size);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);

        transactionRabbitMQTemplateAdapter.send(new String[]{actionLogMessage});
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.processTransaction(..)))", returning = "transactions")
    public void logProcessTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }

        List<String> actionLogMessages = new ArrayList<>();
        transactions.forEach(transaction -> {
            String action = "Process Transaction";
            String details = String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Time: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTime(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            actionLogMessages.add(actionLogMessage);
            if (actionLogMessages.size() == CHUNK_MESSAGE_SIZE)
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
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTime(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            actionLogMessages.add(actionLogMessage);
            if (actionLogMessages.size() == CHUNK_MESSAGE_SIZE)
            {
                transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
                actionLogMessages.clear();
            }
        });

        if (actionLogMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(actionLogMessages.toArray(new String[0]));
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void LogEvent(String[] data) {

        List<Log> actionLogs = new ArrayList<>();
                    for (String actionLogMessage : data) {
            String[] actionLogData = actionLogMessage.split(SEPARATOR);
            Log actionLog = new Log(actionLogData[0], actionLogData[1]);
            actionLogs.add(actionLog);
        }
        actionLogPersistenceAdapter.saveAll(actionLogs);
        //actionLogJdbcTemplateAdapter.saveAll(actionLogs);
        // print count of action logs
        System.out.println(" [x] Received '" + actionLogs.size() + " action logs");
    }
}
