package com.bdgarat.sbmsaccountservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_account")
@Data
@NoArgsConstructor
public class AccountEntity implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 60)
    private String id;
    @Column(name = "account_name", nullable = false, length = 60)
    private String accountName;
    @Column(name = "account_number", nullable = false, length = 13)
    private String accountNumber;
    @Column(name = "account_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal accountBalance;
    @Column(name = "customer_cu", nullable = false, updatable = false, length = 20)
    private String customerCu;

    /*
    @Override
    public AccountDTO getDto() {
        return AccountDTO.builder()
                .id(id)
                .accountName(accountName)
                .accountNumber(accountNumber)
                .accountBalance(accountBalance)
                .customer(CustomerDTO.builder()
                        .cu(this.customerCu)
                        .build())
                .build();
    }

    @Override
    public void setData(AccountDTO accountDTO) {
        this.id = accountDTO.getId();
        this.accountName = accountDTO.getAccountName();
        this.accountNumber = accountDTO.getAccountNumber();
        this.accountBalance = accountDTO.getAccountBalance();
        this.customerCu = accountDTO.getCustomer().getCu();
    }*/
}
