package com.bdgarat.sbmscustomerservice.service;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import com.bdgarat.sbmscustomerservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmscustomerservice.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private ICustomerRepository customerRepository;

    @Cacheable(value = "customersCu",
            key = "#cu",
            unless = "#result == null",
            sync = true)
    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getByCu(String cu) {
        log.info("Get customer by cu {}", cu);
        return customerRepository.findByCu(cu)
                .map(CustomerEntity::getDto)
                .orElse(null);
    }

    @Cacheable(
            value = "customersAll",
            key = "'all'",
            unless = "#result == null || #result.isEmpty()",
            sync = true
    )
    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> getAll() {
        log.info("Get all customers");
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerEntity::getDto)
                .toList();
    }

    @CacheEvict(value = "customersAll", key = "'all'")
    @Caching(put = {
            @CachePut(value = "customersId", key = "#result.id"),
            @CachePut(value = "customersCu", key = "#result.cu")
    })
    @Transactional
    @Override
    public CustomerDTO add(CustomerDTO customerDTO) {
        log.info("Add customer {}", customerDTO);
        boolean exists = this.customerRepository.existsById(customerDTO.getCu());
        if(exists) {
            throw new RuntimeException("Registry already found");
        }
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setData(customerDTO);
        return this.customerRepository.save(customerEntity).getDto();
    }


    @Caching(put = {
            @CachePut(value = "customersId", key = "#result.id"),
            @CachePut(value = "customersCu", key = "#result.cu")
    })
    @CacheEvict(value = "customersAll", key = "'all'")
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
            throw new NoSuchResourceFoundException("Registry NOT found");
        }
    }


    @Caching(evict = {
            @CacheEvict(value = "customersAll", key = "'all'"),
            @CacheEvict(value = "customersId", key = "#customerDTO.id"),
            @CacheEvict(value = "customersCu", key = "#customerDTO.cu")
    })
    @Transactional
    @Override
    public void delete(CustomerDTO customerDTO) {
        log.info("Delete customer {}", customerDTO);
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerDTO.getId());
        if(customerEntity.isPresent()) {
            this.customerRepository.delete(customerEntity.get());
        } else {
            throw new NoSuchResourceFoundException("Registry NOT found");
        }
    }

    @Cacheable(value = "customersId",
            key = "#id",
            unless = "#result == null",
            sync = true)
    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getById(String id) {
        log.info("Get customer by id {}", id);
        return customerRepository.findById(id)
                .map(CustomerEntity::getDto)
                .orElse(null);
    }
}
