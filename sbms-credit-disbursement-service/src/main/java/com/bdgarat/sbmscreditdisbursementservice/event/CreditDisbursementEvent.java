package com.bdgarat.sbmscreditdisbursementservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditDisbursementEvent {

    private String accountNumber;
    private String customerCu;
    private BigDecimal amount;
    private String email;
}
