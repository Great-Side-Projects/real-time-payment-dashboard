package org.dev.paymentwebsocketnotification.application.port;

public interface ITransactionServicePort {
    String sendTransactionWebsocketNotification(String data);
}
