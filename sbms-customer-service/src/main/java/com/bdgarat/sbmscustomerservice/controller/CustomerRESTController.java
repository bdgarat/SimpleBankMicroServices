package com.bdgarat.sbmscustomerservice.controller;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import com.bdgarat.sbmscustomerservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmscustomerservice.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerRESTController {

    private ICustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return this.customerService.getAll();
    }

    @GetMapping("/cu/{cu}")
    public CustomerDTO getCustomerByCu(@PathVariable String cu) {
        CustomerDTO customer = this.customerService.getByCu(cu);
        if (customer == null) {
            log.debug("Customer with Cu {} not found", cu);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with cu " + cu + " not found");
        }
        return customer;
    }

    @GetMapping("/id/{id}")
    public CustomerDTO getCustomerById(@PathVariable String id) {
        CustomerDTO customer = this.customerService.getById(id);
        if (customer == null) {
            log.debug("Customer with Id {} not found", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer with id " + id + " not found");
        }
        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            return this.customerService.add(customerDTO);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PutMapping
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            return this.customerService.update(customerDTO);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            this.customerService.delete(customerDTO);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
