package com.bdgarat.sbmsaccountservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountDTO (String id, String accountNumber, String accountName, BigDecimal accountBalance, CustomerDTO customer) {

}
