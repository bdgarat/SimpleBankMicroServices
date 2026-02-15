package com.bdgarat.sbmsaccountservice.repository;

import com.bdgarat.sbmsaccountservice.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByAccountNumberAndCustomerCu(String accountNumber, String customerCu);
}
