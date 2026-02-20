package com.bdgarat.sbmscustomerservice.repositories;

import com.bdgarat.sbmscustomerservice.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findByCu(String cu);

    boolean existsByCu(String cu);
}
