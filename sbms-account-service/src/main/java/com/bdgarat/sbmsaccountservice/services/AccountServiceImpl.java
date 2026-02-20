package com.bdgarat.sbmsaccountservice.services;

import com.bdgarat.sbmsaccountservice.clients.ICustomerRESTClient;
import com.bdgarat.sbmsaccountservice.dtos.AccountDTO;
import com.bdgarat.sbmsaccountservice.dtos.CustomerDTO;
import com.bdgarat.sbmsaccountservice.dtos.DepositDTO;
import com.bdgarat.sbmsaccountservice.entities.AccountEntity;
import com.bdgarat.sbmsaccountservice.exceptions.ExternalServiceUnreacheable;
import com.bdgarat.sbmsaccountservice.exceptions.NoSuchResourceFoundException;
import com.bdgarat.sbmsaccountservice.mappers.AccountMapper;
import com.bdgarat.sbmsaccountservice.repositories.IAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private IAccountRepository accountRepository;

    private ICustomerRESTClient customerRESTClient;

    private AccountMapper accountMapper;

    @Cacheable(value = "accountsId",
            key = "#id",
            sync = true)
    @Transactional(readOnly = true)
    @Override
    public AccountDTO getById(String id) {
        log.info("Get account by id {}", id);
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElse(null);
    }

    /*@Cacheable(
            value = "accountsAll",
            key = "'all'",
            sync = true
    )*/
    @Transactional(readOnly = true)
    @Override
    public List<AccountDTO> getAll() {
        return this.accountRepository.findAll().
                stream().
                map(accountMapper::toDto).
                toList();
    }

    //@CacheEvict(value = "accountsAll", key = "'all'")
    @CachePut(value = "accountsId", key = "#result.id")
    @Transactional
    @Override
    public AccountDTO add(AccountDTO accountDTO) {
        log.info("Add account {}", accountDTO);
        CustomerDTO customerResponse;
        // Sync call to customer-services
        ResponseEntity<CustomerDTO> responseEntityGetCustomerDTO = this.customerRESTClient.getByCu(accountDTO.customer().cu());
        if(responseEntityGetCustomerDTO.getStatusCode().is2xxSuccessful()) {
            log.info("Customer fetched successfully");
            customerResponse = responseEntityGetCustomerDTO.getBody();
        } else { // If the customer does not exists, we create it
            // Sync call to customer-services
            ResponseEntity<CustomerDTO> responseEntityNewCustomerDTO = this.customerRESTClient.add(accountDTO.customer());
            if(responseEntityNewCustomerDTO.getStatusCode().is2xxSuccessful()) {
                log.info("Customer added successfully");
                customerResponse = responseEntityNewCustomerDTO.getBody();
            } else {
                String error = "Could not fetch nor add the Customer";
                log.debug(error);
                throw new ExternalServiceUnreacheable(error);
            }
        }
        AccountEntity accountEntity = accountMapper.toEntity(accountDTO);
        accountEntity.setCustomerCu(customerResponse.cu());
        log.info("Add account to account repositories: {}", accountDTO);
        return accountMapper.toDto(this.accountRepository.save(accountEntity));
    }

    //@CacheEvict(value = "accountsAll", key = "'all'")
    @CachePut(value = "accountsId", key = "#result.id")
    @Transactional
    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        // Sync call to customer-services
        ResponseEntity<CustomerDTO> responseEntityUpdatedCustomerDTO = this.customerRESTClient.update(accountDTO.customer());
        if(responseEntityUpdatedCustomerDTO.getStatusCode().is2xxSuccessful()) {
            log.info("Customer updated successfully");

            AccountEntity accountEntity = accountMapper.toEntity(accountDTO);
            accountEntity.setCustomerCu(responseEntityUpdatedCustomerDTO.getBody().cu());
            log.info("Update account to account repositories: {}", accountDTO);
            return accountMapper.toDto(this.accountRepository.save(accountEntity));
        } else {
            throw new ExternalServiceUnreacheable("Error updating account to account repositories: " +  responseEntityUpdatedCustomerDTO);
        }
    }

    /*@Caching(evict = {
            //@CacheEvict(value = "accountsAll", key = "'all'"),

    })*/
    @CacheEvict(value = "accountsId", key = "#accountDTO.id")
    @Transactional
    @Override
    public void delete(AccountDTO accountDTO) {
        log.info("remove account from repositories: {}", accountDTO);
        accountRepository.deleteById(accountDTO.id());
    }

    @Transactional
    @Override
    public AccountDTO depositInAccount(DepositDTO depositDTO) {
        log.info("Deposit in customer to account {}", depositDTO);
        Optional<AccountEntity> accountEntityOptional = this.accountRepository.findByAccountNumberAndCustomerCu(depositDTO.accountNumber(), depositDTO.customerCu());
        if(accountEntityOptional.isPresent()) {
            // deposit in account
            AccountEntity accountEntity = accountEntityOptional.get();
            accountEntity.setAccountBalance(accountEntity.getAccountBalance().add(depositDTO.amount()));
            return accountMapper.toDto(this.accountRepository.save(accountEntity));
        } else {
            throw new NoSuchResourceFoundException("No account found with account number " + depositDTO.accountNumber() + " and customer cu " + depositDTO.customerCu());
        }
    }
}
