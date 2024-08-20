package org.dev.paymentingestion.infraestructure.adapter.out;

import org.dev.paymentingestion.application.port.out.ITransactionFormatProviderPort;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TransactionFormatProviderAdapter implements ITransactionFormatProviderPort {

    private final Pattern pattern = Pattern.compile(TRANSACTION_REGEX);
    private static final String TRANSACTION_REGEX = "Log: (\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z) - Transaction \\[([a-f0-9-]+)\\] - User: (\\w+), Amount: \\$(\\d+), Status: (\\w+), Time: (\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z), Location: (\\w+)";

    @Override
    public rTransaction getTransactionFromLine(String line) {
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
            return new rTransaction(logTime, transactionId, userId, amount, status, timestamp, location);
        }
        return null;
    }
}
