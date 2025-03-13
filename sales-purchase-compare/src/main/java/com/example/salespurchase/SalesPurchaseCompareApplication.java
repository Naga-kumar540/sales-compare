package com.example.salespurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class SalesPurchaseCompareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesPurchaseCompareApplication.class, args);
	}

}
