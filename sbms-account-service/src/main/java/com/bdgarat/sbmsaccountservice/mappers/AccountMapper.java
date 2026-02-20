package com.bdgarat.sbmsaccountservice.mappers;

import com.bdgarat.sbmsaccountservice.dtos.AccountDTO;
import com.bdgarat.sbmsaccountservice.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "customerCu", source = "customer.cu")
    AccountEntity toEntity(AccountDTO dto);
    @Mapping(target = "customer.cu", source = "customerCu")
    AccountDTO toDto(AccountEntity entity);
}
