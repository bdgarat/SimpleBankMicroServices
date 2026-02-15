package com.bdgarat.sbmscustomerservice.repository;

import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findByCu(String cu);
}
