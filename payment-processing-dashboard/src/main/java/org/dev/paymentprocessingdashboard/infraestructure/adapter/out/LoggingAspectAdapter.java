package org.dev.paymentprocessingdashboard.infraestructure.adapter.out;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.dev.paymentprocessingdashboard.application.port.ITransactionConvertProviderPort;
import org.dev.paymentprocessingdashboard.application.port.out.ILoggingAspectPort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionEventTemplatePort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LoggingAspectAdapter implements ILoggingAspectPort {

    private final ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter;
    private final ITransactionConvertProviderPort transactionConvertProviderAdapter;
    private final String SEPARATOR = "::";
    private final int CHUNK_MESSAGE_SIZE = 5000;

    public LoggingAspectAdapter(ITransactionEventTemplatePort transactionRabbitMQTemplateAdapter,
                                ITransactionConvertProviderPort transactionConvertProviderAdapter)
    {
        this.transactionRabbitMQTemplateAdapter = transactionRabbitMQTemplateAdapter;
        this.transactionConvertProviderAdapter = transactionConvertProviderAdapter;
    }

    @After("execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.filterTransactions(..)) &&  args(status, userId, minAmount, maxAmount, transactionId, nextPagingState, size)")
    @Override
    public void logFilterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, String nextPagingState, int size) {
        String action = "Filter Transactions";
        String details = String.format("Filter applied - Status: %s, UserId: %s, MinAmount: %s, MaxAmount: %s, TransactionId: %s, Page: %d, Size: %d",
                status, userId, minAmount, maxAmount, transactionId, nextPagingState, size);
        String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);

        transactionRabbitMQTemplateAdapter.send(new String[]{actionLogMessage});
    }

    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.application.service.TransactionService.processTransaction(..)))", returning = "transactions")
    @Override
    public void logProcessTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }

        List<String> logMessages = new ArrayList<>();
        transactions.forEach(transaction -> {
            String action = "Process Transaction";
            String details = String.format("Processed transaction - Id: %s, UserId: %s, Amount: %s, Status: %s, Time: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTime(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            logMessages.add(actionLogMessage);
            if (logMessages.size() == CHUNK_MESSAGE_SIZE)
            {
                transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
                logMessages.clear();
            }
        });

        if (logMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
    }


    @AfterReturning(value = "execution(* org.dev.paymentprocessingdashboard.infraestructure.adapter.out.TransactionSendNotificationAdapter.send(..))", returning = "notifiedTransactions")
    @Override
    public void logNotification(List<Transaction> notifiedTransactions) {

        if (notifiedTransactions.isEmpty()) {
            return;
        }

        List<String> logMessages = new ArrayList<>();
        notifiedTransactions.forEach(transaction -> {
            String action = "Send Notification";
            // clean ANSI color red and

            String details = String.format("Send notification - Id: %s, UserId: %s, Amount: %s, Status: %s, Time: %s, Location: %s",
                    transaction.getId(), transaction.getUserId(), transaction.getAmount(), transaction.getStatus(), transaction.getTime(), transaction.getLocation());
            String actionLogMessage = String.format("%s%s%s", action, SEPARATOR, details);
            logMessages.add(actionLogMessage);
            if (logMessages.size() == CHUNK_MESSAGE_SIZE)
            {
                transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
                logMessages.clear();
            }
        });

        if (logMessages.size() > 0)
            transactionRabbitMQTemplateAdapter.send(logMessages.toArray(new String[0]));
    }
}
