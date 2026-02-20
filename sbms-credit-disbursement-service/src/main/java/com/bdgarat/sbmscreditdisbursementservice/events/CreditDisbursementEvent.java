package com.bdgarat.sbmscreditdisbursementservice.events;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreditDisbursementEvent (String accountNumber, String customerCu, BigDecimal amount, String email) {

}
