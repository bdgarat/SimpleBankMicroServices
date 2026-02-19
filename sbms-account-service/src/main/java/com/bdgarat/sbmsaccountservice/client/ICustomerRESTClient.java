package com.bdgarat.sbmsaccountservice.client;

import com.bdgarat.sbmsaccountservice.dto.CustomerDTO;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${feign.client.customer.name}", url = "${feign.client.customer.url}")
public interface ICustomerRESTClient {

    Logger LOGGER = LoggerFactory.getLogger(ICustomerRESTClient.class);

    @PostMapping
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackAdd")
    ResponseEntity<CustomerDTO> add(@RequestBody CustomerDTO customerDTO);

    @GetMapping("/cu/{cu}")
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackGetByCu")
    ResponseEntity<CustomerDTO> getByCu(@PathVariable String cu);

    default ResponseEntity<CustomerDTO> fallbackAdd(@RequestBody CustomerDTO customerDTO, Throwable throwable) {
        LOGGER.warn("Circuit Breaker - fallbackAdd");
        return new ResponseEntity<>(customerDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    default ResponseEntity<CustomerDTO> fallbackGetByCu(@PathVariable String cu, Throwable throwable) {
        LOGGER.warn("Circuit Breaker - fallbackGetByCu");
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
