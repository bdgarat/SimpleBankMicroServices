package com.bdgarat.sbmscreditdisbursementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SbmsCreditDisbursementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbmsCreditDisbursementServiceApplication.class, args);
    }

}
