package org.dev.paymentnotificationhubs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentNotificationHubsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentNotificationHubsApplication.class, args);
        System.out.println("payment-notification-hubs started...");

    }
}
