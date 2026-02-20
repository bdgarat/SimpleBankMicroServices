package com.bdgarat.sbmscreditdisbursementservice.client;

import com.bdgarat.sbmscreditdisbursementservice.dto.AccountDTO;
import com.bdgarat.sbmscreditdisbursementservice.dto.DepositDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "${feign.client.account.name}", url="${feign.client.account.url}")
public interface IAccountRESTClient {

    @PutMapping("/deposit")
    ResponseEntity<AccountDTO> depositInAccount(@RequestBody DepositDTO depositDTO);
}
