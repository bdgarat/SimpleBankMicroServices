package com.bdgarat.sbmsaccountservice.mappers;

import com.bdgarat.sbmsaccountservice.dto.AccountDTO;
import com.bdgarat.sbmsaccountservice.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "customerCu", source = "customer.cu")
    AccountEntity toEntity(AccountDTO dto);
    @Mapping(target = "customer.cu", source = "customerCu")
    AccountDTO toDto(AccountEntity entity);
}
