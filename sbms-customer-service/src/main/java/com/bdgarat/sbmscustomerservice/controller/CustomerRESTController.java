package com.bdgarat.sbmscustomerservice.controller;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmscustomerservice.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerRESTController {

    private ICustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        if (!CustomerDTO.isValid(customerDTO)) {
            throw new BadResourceRequestException("The customer DTO is invalid");
        }
        try {
            return this.customerService.add(customerDTO);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/cu/{cu}")
    public CustomerDTO getCustomerByCu(@PathVariable String cu) {
        return this.customerService.getByCu(cu);
    }
}
