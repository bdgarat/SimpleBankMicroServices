package com.bdgarat.sbmscustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SbmsCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbmsCustomerServiceApplication.class, args);
	}

}
