package com.bdgarat.sbmscreditdisbursementservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreditDisbursementDTO(String id, BigDecimal amount,Integer termMonths, BigDecimal interestRate, String accountNumber, String customerCu) {
}
