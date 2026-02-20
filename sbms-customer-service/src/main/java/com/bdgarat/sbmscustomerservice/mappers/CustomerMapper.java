package com.bdgarat.sbmscustomerservice.mappers;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity toEntity(CustomerDTO dto);
    CustomerDTO toDto(CustomerEntity entity);
}
