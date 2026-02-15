package com.bdgarat.sbmsaccountservice.controller;

import com.bdgarat.sbmsaccountservice.dto.AccountDTO;
import com.bdgarat.sbmsaccountservice.dto.DepositDTO;
import com.bdgarat.sbmsaccountservice.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountRESTController {

    private IAccountService accountService;

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return this.accountService.getAll();
    }

    @PostMapping
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        return this.accountService.add(accountDTO);
    }

    @PutMapping
    public AccountDTO depositAccount(@RequestBody DepositDTO depositDTO) {
        return this.accountService.depositInAccount(depositDTO);
    }
}
