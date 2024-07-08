package org.dev.paymentprocessingdashboard.domain;

import org.dev.paymentprocessingdashboard.application.port.ITransactionFormatProviderPort;

public class Transaction {
    private String id;
    private String userid;
    private double amount;
    private String status;
    private String timestamp;
    private String location;


    public Transaction(String id, String userid, double amount, String status, String timestamp, String location) {
        this.id = id;
        this.userid = userid;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userid;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
    public static Transaction processLine(String line, ITransactionFormatProviderPort transactionFormatProviderPort){
        ITransactionFormatProviderPort.rTransaction rTransaction = transactionFormatProviderPort.getTransactionFromLine(line);
        if (rTransaction == null)
            return null;
        return new Transaction(rTransaction.transactionId(), rTransaction.userId(), rTransaction.amount(), rTransaction.status(), rTransaction.timestamp(), rTransaction.location());
    }
}
