package org.dev.paymentprocessingdashboard.application;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;

@Aspect
@Component
public class LoggingAspect {

    private final ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter;
    private final IActionLogPersistencePort actionLogPersistenceAdapter;
    private final String SEPARATOR = "::";

    public LoggingAspect(ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter, IActionLogPersistencePort actionLogPersistenceAdapter) {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
        this.actionLogPersistenceAdapter = actionLogPersistenceAdapter;
    }

    //Todo: cath exception and log it
    @Before("execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.filterTransactions(..)) && args(status, userId, minAmount, maxAmount, transactionId, page, size)")
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size) throws InterruptedException {
        //Todo - Refactor whit RabbitMQ
        String action = "Filter Transactions";
        String details = String.format("Filter applied - Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %d, Size: %d",
                status, userId, minAmount, maxAmount, transactionId, page, size);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR,details);
        transactionRabbitMQTemplateAdapter.send(actionLogMessage);
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.application.service.LogReaderService.readLogFile(..)))", returning = "transactions")
    public void logProcessTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }
        //Todo - Refactor whit RabbitMQ
        //List<String> detailsList = transactions.stream()
        //        .map(transaction -> String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Timestamp: %s, Location: %s",
        //                transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTimestamp(), transaction.getLocation()))
        //        .toList();
        //actionLogPersistenceAdapter.saveAll("Process Transaction", detailsList);

        transactions.forEach(transaction -> {
            String action = "Process Transaction";
            String details = String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Timestamp: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTimestamp(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR,details);
            transactionRabbitMQTemplateAdapter.send(actionLogMessage);
        });
        //notification send
    }

    @After("execution(* org.dev.paymentprocessingdashboard.infraestructure.adapter.out.TransactionWebSocketAdapter.send(..)) && args(data)")
    public void logNotification(String data) {

        String action = "Notification";
        String details = String.format("Notification sent - %s", data);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR,details);
        transactionRabbitMQTemplateAdapter.send(actionLogMessage);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void actionLogEvent(String data) {
        data = data.replace("\u001B[31m", "").replace("\u001B[33m", "");
        String[] actionLog = data.split(SEPARATOR);
        String action = actionLog[0];
        String details = actionLog[1];
        actionLogPersistenceAdapter.save(action, details);
        System.out.println(" [x] Received '" + data + "'");
    }
}
