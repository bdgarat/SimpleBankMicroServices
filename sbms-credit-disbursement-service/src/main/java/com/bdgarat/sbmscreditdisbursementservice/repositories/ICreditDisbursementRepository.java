package com.bdgarat.sbmscreditdisbursementservice.repositories;

import com.bdgarat.sbmscreditdisbursementservice.entities.CreditDisbursementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditDisbursementRepository extends JpaRepository<CreditDisbursementEntity, String> {

}
