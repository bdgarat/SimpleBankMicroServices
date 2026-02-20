package com.bdgarat.sbmscreditdisbursementservice.dtos;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositDTO (String accountNumber, BigDecimal amount, String customerCu) {

}
