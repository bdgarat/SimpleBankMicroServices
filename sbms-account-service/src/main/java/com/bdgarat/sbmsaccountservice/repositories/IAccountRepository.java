package com.bdgarat.sbmsaccountservice.repositories;

import com.bdgarat.sbmsaccountservice.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByAccountNumberAndCustomerCu(String accountNumber, String customerCu);
}
