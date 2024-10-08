package org.dev.paymentingestion.domain;

import org.dev.paymentingestion.application.port.out.ITransactionConvertProviderPort;
import org.dev.paymentingestion.application.port.out.ITransactionFormatProviderPort;

public class Transaction {
    private String id;
    private String userid;
    private double amount;
    private String status;
    private String time;
    private String location;


    public Transaction(String id, String userid, double amount, String status, String time, String location) {
        this.id = id;
        this.userid = userid;
        this.amount = amount;
        this.status = status;
        this.time = time;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return """
                {"Transaction":{"id":'%s', "userid":'%s', "amount":%f, "status":'%s', "time":'%s', "location":'%s'}}"""
                .formatted(id, userid, amount, status, time, location);
    }
    public static Transaction processLine(String line, ITransactionFormatProviderPort transactionFormatProviderPort){
        ITransactionFormatProviderPort.rTransaction rTransaction = transactionFormatProviderPort.getTransactionFromLine(line);
        if (rTransaction == null)
            return null;
        return new Transaction(rTransaction.transactionId(), rTransaction.userId(), rTransaction.amount(), rTransaction.status(), rTransaction.timestamp(), rTransaction.location());
    }

    public static Transaction loadFromStringJson(String json, ITransactionConvertProviderPort transactionCeonverterProviderPort){
      return transactionCeonverterProviderPort.loadFromStringJson(json);
    }
}
