package com.bdgarat.sbmscreditdisbursementservice.mappers;

import com.bdgarat.sbmscreditdisbursementservice.dto.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.entity.CreditDisbursementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditDisbursementMapper {
    CreditDisbursementEntity toEntity(CreditDisbursementDTO dto);
    CreditDisbursementDTO toDto(CreditDisbursementEntity entity);
}