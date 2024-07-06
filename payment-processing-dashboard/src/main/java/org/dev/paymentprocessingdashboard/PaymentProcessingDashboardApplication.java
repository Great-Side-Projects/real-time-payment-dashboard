package org.dev.paymentprocessingdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
public class PaymentProcessingDashboardApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(PaymentProcessingDashboardApplication.class, args);
        System.out.println("Payment Processing Dashboard is running...");
    }
}
