package org.dev.paymentprocessingdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentProcessingDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentProcessingDashboardApplication.class, args);
        System.out.println("Payment Processing Dashboard is running...");
    }
}
