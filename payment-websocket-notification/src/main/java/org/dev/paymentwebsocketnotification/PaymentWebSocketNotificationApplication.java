package org.dev.paymentwebsocketnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentWebSocketNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentWebSocketNotificationApplication.class, args);
        System.out.println("payment-websocket-notification started...");

    }
}
