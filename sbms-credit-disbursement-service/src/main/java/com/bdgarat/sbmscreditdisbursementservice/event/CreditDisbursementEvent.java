package com.bdgarat.sbmscreditdisbursementservice.event;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreditDisbursementEvent (String accountNumber, String customerCu, BigDecimal amount, String email) {

}
