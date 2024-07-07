package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionPersistenceAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UseCase
public class LogReaderService {

    private static final String LOG_FILE_PATH = "/home/angel/Documents/paraborrar/real-time-payment-dashboard/log-generator/logs/transactions.log";
    private static final String TRANSACTION_REGEX = "Log: (\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z) - Transaction \\[([a-f0-9-]+)\\] - User: (\\w+), Amount: \\$(\\d+), Status: (\\w+), Time: (\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z), Location: (\\w+)";
    private final Pattern pattern = Pattern.compile(TRANSACTION_REGEX);
    private final Path path = Paths.get(LOG_FILE_PATH);
    private final TransactionPersistenceAdapter transactionPersistenceAdapter;
    private long lastKnownPosition = 0;
    private final String ANSIBLUE = "\u001B[34m";
    private final String ANSIRESET = "\u001B[0m";
    private final String ANSIRED = "\u001B[31m";
    private final String ANSIYELLOW = "\u001B[33m";
    private final SimpMessagingTemplate messagingTemplate;
    public LogReaderService(TransactionPersistenceAdapter transactionPersistenceAdapter, SimpMessagingTemplate messagingTemplate) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 500) // Cada 1 segundos
    public List<Transaction> readLogFile() throws IOException {

        //Todo: read file with relative path
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            file.seek(lastKnownPosition);
            String line;
            List<Transaction> transactions = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                //Todo: processline in a separate thread to improve performance and the concert is by Transaction object, order by arrive line
                Transaction transaction = processLine(line);
                transactions.add(transaction);
            }
            if (transactions.isEmpty()) {
                //ahother way to return transactions empty
                return List.of();
            }
            transactionPersistenceAdapter.saveAll(transactions);
            transactions.forEach(transaction -> {
                System.out.println(ANSIBLUE + transaction.toString());

                if (transaction.getStatus().equals("failure")) {
                    messagingTemplate.convertAndSend("/topic/notifications",ANSIRED+transaction);
                }
                else if (transaction.getAmount() > 1000) {
                    messagingTemplate.convertAndSend("/topic/notifications", ANSIYELLOW+transaction);
                }
            });

           System.out.println(ANSIRESET+transactionPersistenceAdapter.totalTransactionSummary());
           System.out.println(transactionPersistenceAdapter.summaryTransactionsPerMinute());
           lastKnownPosition = file.getFilePointer();
           return transactions;
        }
    }

    private Transaction processLine(String line) {
        // Parse the line and create a Transaction object
        // Log: 2024-07-04T15:33:20.537Z - Transaction [e6c1434e-9c5f-4dde-9b01-ec60daa8ff0d] - User: U1, Amount: $4306, Status: success, Time: 2024-07-04T15:33:20.537Z, Location: NY
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String logTime = matcher.group(1);
            String transactionId = matcher.group(2);
            String userId = matcher.group(3);
            double amount = Double.parseDouble(matcher.group(4));
            String status = matcher.group(5);
            String timestamp = matcher.group(6);
            String location = matcher.group(7);
            return new Transaction(transactionId, userId, amount, status, timestamp, location);
        }
      return null;
    }
}

