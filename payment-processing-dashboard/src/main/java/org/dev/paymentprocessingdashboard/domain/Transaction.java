package org.dev.paymentprocessingdashboard.domain;

public class Transaction {
    private String id;
    private String user;
    private double amount;
    private String status;
    private String timestamp;
    private String location;

    public Transaction(String id, String user, double amount, String status, String timestamp, String location) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
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
                ", user='" + user + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
