package com.bdgarat.sbmscustomerservice.service;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import com.bdgarat.sbmscustomerservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmscustomerservice.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private ICustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getByCu(String cu) {
        log.info("Get customer by cu {}", cu);
        Optional<CustomerEntity> en = this.customerRepository.findByCu(cu);
        if(en.isPresent()) {
            return en.get().getDto();
        } else {
            log.info("Customer with cu NOT found {}", cu);
            return CustomerDTO.builder().build();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> getAll() {
        log.info("Get all customers");
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerEntity::getDto)
                .toList();
    }

    @Transactional
    @Override
    public CustomerDTO add(CustomerDTO customerDTO) {
        log.info("Add customer {}", customerDTO);
        boolean exists = this.customerRepository.existsById(customerDTO.getCu());
        if(exists) {
            log.warn("Customer with id does already exist: {}", customerDTO.getId());
            throw new RuntimeException("Registry already found");
        }
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setData(customerDTO);
        return this.customerRepository.save(customerEntity).getDto();
    }

    @Transactional
    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.info("Update customer {}", customerDTO);
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerDTO.getId());
        if(customerEntity.isPresent()) {
            CustomerEntity customer = customerEntity.get();
            customer.setData(customerDTO);
            return this.customerRepository.save(customer).getDto();
        } else {
            log.warn("Customer with id does NOT exist: {}", customerDTO.getId());
            throw new NoSuchResourceFoundException("Registry NOT found");
        }
    }

    @Transactional
    @Override
    public void delete(CustomerDTO customerDTO) {
        log.info("Delete customer {}", customerDTO);
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerDTO.getId());
        if(customerEntity.isPresent()) {
            this.customerRepository.delete(customerEntity.get());
        } else {
            log.warn("Customer with id does NOT exist: {}", customerDTO.getId());
            throw new NoSuchResourceFoundException("Registry NOT found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getById(String id) {
        log.info("Get customer by id {}", id);
        Optional<CustomerEntity> en = this.customerRepository.findById(id);
        if(en.isPresent()) {
            return en.get().getDto();
        } else {
            log.info("Customer with id does NOT exist: {}", id);
            return CustomerDTO.builder().build();
        }
    }
}
