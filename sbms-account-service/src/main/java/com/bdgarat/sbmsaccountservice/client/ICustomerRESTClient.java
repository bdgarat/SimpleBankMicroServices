package com.bdgarat.sbmsaccountservice.client;

import com.bdgarat.sbmsaccountservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${feign.client.customer.name}", url = "${feign.client.customer.url}")
public interface ICustomerRESTClient {

    @PostMapping
    ResponseEntity<CustomerDTO> add(@RequestBody CustomerDTO customerDTO);

    @GetMapping("/cu/{cu}")
    ResponseEntity<CustomerDTO> getByCu(@PathVariable String cu);
}
