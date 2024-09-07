package org.dev.paymentingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentIngestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentIngestionApplication.class, args);
		System.out.println("Payment Ingestion Application started...-");
	}
}
