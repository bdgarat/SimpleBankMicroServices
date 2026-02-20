package com.bdgarat.sbmscreditdisbursementservice.clients;

import com.bdgarat.sbmscreditdisbursementservice.dtos.AccountDTO;
import com.bdgarat.sbmscreditdisbursementservice.dtos.DepositDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "${feign.client.account.name}", url="${feign.client.account.url}")
public interface IAccountRESTClient {

    Logger LOGGER = LoggerFactory.getLogger(IAccountRESTClient.class);

    @PutMapping("/deposit")
    @CircuitBreaker(name = "${feign.client.account.name}", fallbackMethod = "fallbackDepositInAccount")
    ResponseEntity<AccountDTO> depositInAccount(@RequestBody DepositDTO depositDTO);

    default ResponseEntity<DepositDTO> fallbackDepositInAccount(@RequestBody DepositDTO depositDTO, Throwable throwable) {
        LOGGER.warn("fallbackDepositInAccount");
        return new ResponseEntity<>(depositDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
