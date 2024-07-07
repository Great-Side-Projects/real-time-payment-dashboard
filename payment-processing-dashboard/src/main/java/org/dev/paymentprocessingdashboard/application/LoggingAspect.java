package org.dev.paymentprocessingdashboard.application;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LoggingAspect {

   private final IActionLogPersistencePort actionLogPersistenceAdapter;

    public LoggingAspect(IActionLogPersistencePort actionLogPersistenceAdapter) {
        this.actionLogPersistenceAdapter = actionLogPersistenceAdapter;
    }

    @Before("execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.filterTransactions(..)) && args(status, userId, minAmount, maxAmount, transactionId, page, size)")
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size) throws InterruptedException {
        //Todo - Refactor whit RabbitMQ
        String details = String.format("Filter applied - Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %d, Size: %d", status, userId, minAmount, maxAmount, transactionId, page, size);
        actionLogPersistenceAdapter.save("Filter Transactions", details);
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.application.service.LogReaderService.readLogFile(..)))",returning = "transactions")
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
            String details = String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Timestamp: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTimestamp(), transaction.getLocation());
            actionLogPersistenceAdapter.save("Process Transaction", details);
        });
    }
}
