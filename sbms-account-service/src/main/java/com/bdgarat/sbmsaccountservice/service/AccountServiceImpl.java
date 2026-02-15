package com.bdgarat.sbmsaccountservice.service;

import com.bdgarat.sbmsaccountservice.client.ICustomerRESTClient;
import com.bdgarat.sbmsaccountservice.dto.AccountDTO;
import com.bdgarat.sbmsaccountservice.dto.CustomerDTO;
import com.bdgarat.sbmsaccountservice.dto.DepositDTO;
import com.bdgarat.sbmsaccountservice.entity.AccountEntity;
import com.bdgarat.sbmsaccountservice.repository.IAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private IAccountRepository accountRepository;

    private ICustomerRESTClient customerRESTClient;

    @Override
    public List<AccountDTO> getAll() {
        return this.accountRepository.findAll().stream().map(AccountEntity::getDto).toList();
    }

    @Override
    public AccountDTO add(AccountDTO accountDTO) {

        if(accountDTO.getId() != null) {
            boolean exists = this.accountRepository.existsById(accountDTO.getId());
            if (exists) {
                log.warn("Account with id does already exist: {}", accountDTO.getId());
                throw new RuntimeException("Registry already found");
            }
        }
        ResponseEntity<CustomerDTO> responseEntityNewCustomerDTO = this.customerRESTClient.add(accountDTO.getCustomer());
        if(responseEntityNewCustomerDTO.getStatusCode().is2xxSuccessful()) {
            log.info("Customer added successfully");
            accountDTO.setCustomer(responseEntityNewCustomerDTO.getBody());
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setData(accountDTO);
            log.info("Add account to account repository: {}", accountDTO);
            return this.accountRepository.save(accountEntity).getDto();
        } else {
            log.error("Error adding account to account repository: {}", responseEntityNewCustomerDTO);
            return new AccountDTO();
        }




    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public void delete(AccountDTO accountDTO) {
        log.info("remove account from repository: {}", accountDTO);
        boolean exists = this.accountRepository.existsById(accountDTO.getId());
        if(!exists) {
            log.warn("Account with id does not exist: {}", accountDTO.getId());
            throw new RuntimeException("Registry not found");
        }
        accountRepository.deleteById(accountDTO.getId());
    }

    @Override
    public AccountDTO getById(String id) {
        log.info("Get account by id {}", id);

        return null;
    }

    @Override
    public AccountDTO depositInAccount(DepositDTO depositDTO) {
        log.info("Deposit in customer to account {}", depositDTO);
        Optional<AccountEntity> accountEntityOptional = this.accountRepository.findByAccountNumberAndCustomerCu(depositDTO.getAccountNumber(), depositDTO.getCustomerCu());
        if(accountEntityOptional.isPresent()) {
            // deposit in account
            AccountEntity accountEntity = accountEntityOptional.get();
            accountEntity.setAccountBalance(accountEntity.getAccountBalance().add(depositDTO.getAmount()));
            return this.accountRepository.save(accountEntity).getDto();
        }
        return AccountDTO.builder().build();
    }
}
