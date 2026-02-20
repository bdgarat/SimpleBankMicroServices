package com.bdgarat.sbmsaccountservice.clients;

import com.bdgarat.sbmsaccountservice.dtos.CustomerDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${feign.client.customer.name}", url = "${feign.client.customer.url}")//, fallback = CustomerRESTClientFallback.class)
public interface ICustomerRESTClient {

    Logger LOGGER = LoggerFactory.getLogger(ICustomerRESTClient.class);

    @GetMapping("/cu/{cu}")
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackGetByCu")
    ResponseEntity<CustomerDTO> getByCu(@PathVariable String cu);

    @GetMapping("/cu/{cu}")
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackGetById")
    ResponseEntity<CustomerDTO> getById(@PathVariable String id);

    @PostMapping
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackAdd")
    ResponseEntity<CustomerDTO> add(@RequestBody CustomerDTO customerDTO);

    @PostMapping
    @CircuitBreaker(name = "${feign.client.customer.name}", fallbackMethod = "fallbackUpdate")
    ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO customerDTO);

    default ResponseEntity<CustomerDTO> fallbackGetByCu(String cu, Throwable throwable) {
        LOGGER.warn("fallbackGetByCu");
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    default ResponseEntity<CustomerDTO> fallbackGetById(String cu, Throwable throwable) {
        LOGGER.warn("fallbackGetById");
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    default ResponseEntity<CustomerDTO> fallbackAdd(@RequestBody CustomerDTO customerDTO, Throwable throwable) {
        LOGGER.warn("fallbackAdd");
        return new ResponseEntity<>(customerDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    default ResponseEntity<CustomerDTO> fallbackUpdate(@RequestBody CustomerDTO customerDTO, Throwable throwable) {
        LOGGER.warn("fallbackUpdate");
        return new ResponseEntity<>(customerDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
