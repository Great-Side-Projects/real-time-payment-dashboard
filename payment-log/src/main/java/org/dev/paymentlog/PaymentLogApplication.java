package org.dev.paymentlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentLogApplication.class, args);
        System.out.println("payment-log started...");
        System.console().readLine();
    }
}
