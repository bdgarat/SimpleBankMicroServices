package com.bdgarat.sbmsaccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SbmsAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbmsAccountServiceApplication.class, args);
	}

}
