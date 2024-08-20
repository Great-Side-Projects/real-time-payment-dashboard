package org.dev.paymentingestion.application.port.out;

public interface ITransactionFormatProviderPort {

    record rTransaction(String logTime, String transactionId, String userId, double amount, String status, String timestamp, String location) {}
    rTransaction getTransactionFromLine(String line);
}
