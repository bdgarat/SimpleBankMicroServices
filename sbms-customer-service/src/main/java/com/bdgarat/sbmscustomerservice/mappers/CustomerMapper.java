package com.bdgarat.sbmscustomerservice.mappers;

import com.bdgarat.sbmscustomerservice.dtos.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entities.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity toEntity(CustomerDTO dto);
    CustomerDTO toDto(CustomerEntity entity);
}
