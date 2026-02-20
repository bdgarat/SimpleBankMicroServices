package com.bdgarat.sbmscreditdisbursementservice.mappers;

import com.bdgarat.sbmscreditdisbursementservice.dtos.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.entities.CreditDisbursementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditDisbursementMapper {
    CreditDisbursementEntity toEntity(CreditDisbursementDTO dto);
    CreditDisbursementDTO toDto(CreditDisbursementEntity entity);
}