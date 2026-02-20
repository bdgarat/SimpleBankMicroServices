package com.bdgarat.sbmsaccountservice.dtos;

import lombok.Builder;

@Builder
public record CustomerDTO(String id, String cu, String name, String email, String mobile, String address){

}
