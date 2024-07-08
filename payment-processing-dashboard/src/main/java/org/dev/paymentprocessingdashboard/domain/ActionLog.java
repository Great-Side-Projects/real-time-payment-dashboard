package org.dev.paymentprocessingdashboard.domain;

public class ActionLog {
    private String action;
    private String details;

    public ActionLog(String action, String details) {
        this.action = action;
        this.details = details;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ActionLog{" +
                "action='" + action + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
