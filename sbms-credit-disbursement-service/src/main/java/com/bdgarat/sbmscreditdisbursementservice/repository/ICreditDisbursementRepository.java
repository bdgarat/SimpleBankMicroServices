package com.bdgarat.sbmscreditdisbursementservice.repository;

import com.bdgarat.sbmscreditdisbursementservice.entity.CreditDisbursementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditDisbursementRepository extends JpaRepository<CreditDisbursementEntity, String> {

}
