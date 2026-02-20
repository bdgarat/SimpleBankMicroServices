package com.bdgarat.sbmscustomerservice.services;

import com.bdgarat.sbmscustomerservice.dtos.CustomerDTO;
import com.bdgarat.sbmscustomerservice.utils.ICrud;

public interface ICustomerService extends ICrud<CustomerDTO> {

    CustomerDTO getByCu(String cu);
}
