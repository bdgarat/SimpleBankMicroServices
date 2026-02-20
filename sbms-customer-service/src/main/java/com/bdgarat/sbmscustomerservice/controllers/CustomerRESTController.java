package com.bdgarat.sbmscustomerservice.controllers;

import com.bdgarat.sbmscustomerservice.dtos.CustomerDTO;
import com.bdgarat.sbmscustomerservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmscustomerservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmscustomerservice.services.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerRESTController {

    private ICustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return this.customerService.getAll();
    }

    @GetMapping("/cu/{cu}")
    public CustomerDTO getByCu(@PathVariable String cu) {
        CustomerDTO customer = this.customerService.getByCu(cu);
        if (customer == null) {
            String error = "Customer with Cu " + cu + " not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
        return customer;
    }

    @GetMapping("/id/{id}")
    public CustomerDTO getById(@PathVariable String id) {
        CustomerDTO customer = this.customerService.getById(id);
        if (customer == null) {
            String error = "Customer with Id " + id + " not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO add(@RequestBody CustomerDTO customerDTO) {
        if(customerDTO == null) {
            String error = "Customer DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        return this.customerService.add(customerDTO);
    }

    @PutMapping
    public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
        if(customerDTO == null) {
            String error = "Customer DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        return this.customerService.update(customerDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody CustomerDTO customerDTO) {
        if(customerDTO == null) {
            String error = "Customer DTO is null";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        this.customerService.delete(customerDTO);
    }
}
