package com.bdgarat.sbmscustomerservice.dtos;

import lombok.Builder;

@Builder
public record CustomerDTO(String id, String cu, String name, String email, String mobile, String address){

}
