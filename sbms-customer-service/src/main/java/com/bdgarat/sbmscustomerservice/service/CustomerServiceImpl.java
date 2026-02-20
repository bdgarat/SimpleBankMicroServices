package com.bdgarat.sbmscustomerservice.service;

import com.bdgarat.sbmscustomerservice.dto.CustomerDTO;
import com.bdgarat.sbmscustomerservice.entity.CustomerEntity;
import com.bdgarat.sbmscustomerservice.exceptions.BadResourceRequestException;
import com.bdgarat.sbmscustomerservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmscustomerservice.mappers.CustomerMapper;
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
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private CustomerMapper customerMapper;
    private ICustomerRepository customerRepository;

    @Cacheable(value = "customersId",
            key = "#id",
            sync = true)
    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getById(String id) {
        log.info("Get customer by id {}", id);
        return customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    @Cacheable(value = "customersCu",
            key = "#cu",
            sync = true)
    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getByCu(String cu) {
        log.info("Get customer by cu {}", cu);
        return customerRepository.findByCu(cu)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    /*@Cacheable(
            value = "customersAll",
            key = "'all'",
            sync = true
    )*/
    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> getAll() {
        log.info("Get all customers");
        return this.customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    //@CacheEvict(value = "customersAll", key = "'all'")
    @Caching(put = {
            @CachePut(value = "customersId", key = "#result.id"),
            @CachePut(value = "customersCu", key = "#result.cu")
    })
    @Transactional
    @Override
    public CustomerDTO add(CustomerDTO customerDTO) {
        log.info("Add customer {}", customerDTO);
        boolean exists = this.customerRepository.existsByCu(customerDTO.cu());
        if(exists) {
            String error = "Registry already exists";
            log.debug(error);
            throw new BadResourceRequestException(error);
        }
        CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);
        return customerMapper.toDto(this.customerRepository.save(customerEntity));
    }


    @Caching(put = {
            @CachePut(value = "customersId", key = "#result.id"),
            @CachePut(value = "customersCu", key = "#result.cu")
    })
    //@CacheEvict(value = "customersAll", key = "'all'")
    @Transactional
    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        log.info("Update customer {}", customerDTO);
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerDTO.id());
        if(customerEntity.isPresent()) {
            CustomerEntity customer = customerMapper.toEntity(customerDTO);
            customer.setCu(customerDTO.cu());
            customer.setId(customerDTO.id());
            return customerMapper.toDto(this.customerRepository.save(customer));
        } else {
            String error = "Registry to update not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
    }


    @Caching(evict = {
            //@CacheEvict(value = "customersAll", key = "'all'"),
            @CacheEvict(value = "customersId", key = "#customerDTO.id"),
            @CacheEvict(value = "customersCu", key = "#customerDTO.cu")
    })
    @Transactional
    @Override
    public void delete(CustomerDTO customerDTO) {
        log.info("Delete customer {}", customerDTO);
        boolean exists = this.customerRepository.existsById(customerDTO.id());
        if(exists) {
            this.customerRepository.deleteById(customerDTO.id());
        } else {
            String error = "Registry to delete not found";
            log.debug(error);
            throw new NoSuchResourceFoundException(error);
        }
    }


}
