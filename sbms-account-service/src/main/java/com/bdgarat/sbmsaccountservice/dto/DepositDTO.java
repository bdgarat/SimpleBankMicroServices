package com.bdgarat.sbmsaccountservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositDTO (String accountNumber, BigDecimal amount, String customerCu) {

}
