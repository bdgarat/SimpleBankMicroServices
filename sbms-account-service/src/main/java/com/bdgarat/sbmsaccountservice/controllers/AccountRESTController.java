package com.bdgarat.sbmsaccountservice.controllers;

import com.bdgarat.sbmsaccountservice.dtos.AccountDTO;
import com.bdgarat.sbmsaccountservice.dtos.DepositDTO;
import com.bdgarat.sbmsaccountservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmsaccountservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmsaccountservice.services.IAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountRESTController {

    private IAccountService accountService;

    @GetMapping
    public List<AccountDTO> getAll() {
        return this.accountService.getAll();
    }

    @GetMapping("/id/{id}")
    public AccountDTO getById(@PathVariable String id) {
        AccountDTO account = this.accountService.getById(id);
        if (account == null) {
            String error = "Account with Id " + id + " not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
        return account;
    }

    @PostMapping
    public AccountDTO add(@RequestBody AccountDTO accountDTO) {
        if(accountDTO == null) {
            String error = "Account DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        return this.accountService.add(accountDTO);
    }

    @PutMapping
    public AccountDTO update(@RequestBody AccountDTO accountDTO) {
        if(accountDTO == null) {
            String error = "Account DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        return this.accountService.update(accountDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody AccountDTO accountDTO) {
        if(accountDTO == null) {
            String error = "Account DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        this.accountService.delete(accountDTO);
    }

    @PutMapping("/deposit")
    public AccountDTO depositAccount(@RequestBody DepositDTO depositDTO) {
        return this.accountService.depositInAccount(depositDTO);
    }
}
