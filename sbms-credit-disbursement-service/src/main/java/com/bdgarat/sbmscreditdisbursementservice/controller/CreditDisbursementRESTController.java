package com.bdgarat.sbmscreditdisbursementservice.controller;

import com.bdgarat.sbmscreditdisbursementservice.dto.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.service.ICreditDisbursementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-disbursements")
@AllArgsConstructor
public class CreditDisbursementRESTController {

    private final ICreditDisbursementService creditDisbursementService;

    @GetMapping
    public List<CreditDisbursementDTO> getAllCreditDisbursements() {
        return this.creditDisbursementService.getAll();
    }

    @PostMapping
    public CreditDisbursementDTO addCreditDisbursement(@RequestBody CreditDisbursementDTO creditDisbursementDTO) {
        return this.creditDisbursementService.add(creditDisbursementDTO);
    }
}
