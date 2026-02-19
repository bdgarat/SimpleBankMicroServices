package com.bdgarat.sbmscustomerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String id;
    private String cu;
    private String name;
    private String email;
    private String mobile;
    private String address;

}
