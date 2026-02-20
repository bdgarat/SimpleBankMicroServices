package com.bdgarat.sbmscreditdisbursementservice.controllers;

import com.bdgarat.sbmscreditdisbursementservice.dtos.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmscreditdisbursementservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmscreditdisbursementservice.services.ICreditDisbursementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/credit-disbursements")
@AllArgsConstructor
public class CreditDisbursementRESTController {

    private final ICreditDisbursementService creditDisbursementService;

    @GetMapping("/id/{id}")
    public CreditDisbursementDTO getById(@PathVariable String id) {
        CreditDisbursementDTO account = this.creditDisbursementService.getById(id);
        if (account == null) {
            String error = "Credit disbursement with Id " + id + " not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
        return account;
    }

    @GetMapping
    public List<CreditDisbursementDTO> getAllCreditDisbursements() {
        return this.creditDisbursementService.getAll();
    }

    @PostMapping
    public CreditDisbursementDTO addCreditDisbursement(@RequestBody CreditDisbursementDTO creditDisbursementDTO) {
        if(creditDisbursementDTO == null) {
            String error = "creditDisbursement DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        return this.creditDisbursementService.add(creditDisbursementDTO);
    }
}
