package org.proj3ct.transactionservive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TransactionServiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiveApplication.class, args);
	}

}
