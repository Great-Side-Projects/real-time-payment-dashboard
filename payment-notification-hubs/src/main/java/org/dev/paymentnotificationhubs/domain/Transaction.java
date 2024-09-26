package org.dev.paymentnotificationhubs.domain;

public class Transaction  {
    private String id;
    private String userid;
    private double amount;
    private String status;
    private String time;
    private String location;

    public Transaction() {
    }

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
                "transaction": {"id": "%s", "userid": "%s", "amount": %f, "status": "%s", "time": "%s", "location": "%s"}"""
                .formatted(id, userid, amount, status, time, location);
    }
}
