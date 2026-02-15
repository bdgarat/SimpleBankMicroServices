package com.bdgarat.sbmscreditdisbursementservice.service;

import com.bdgarat.sbmscreditdisbursementservice.client.IAccountRESTClient;
import com.bdgarat.sbmscreditdisbursementservice.config.EnqueueDequeService;
import com.bdgarat.sbmscreditdisbursementservice.dto.AccountDTO;
import com.bdgarat.sbmscreditdisbursementservice.dto.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.dto.DepositDTO;
import com.bdgarat.sbmscreditdisbursementservice.entity.CreditDisbursementEntity;
import com.bdgarat.sbmscreditdisbursementservice.event.CreditDisbursementEvent;
import com.bdgarat.sbmscreditdisbursementservice.repository.ICreditDisbursementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class CreditDisbursementServiceImpl implements ICreditDisbursementService {

    private final ICreditDisbursementRepository creditDisbursementRepository;
    private final IAccountRESTClient accountRESTClient;
    private final EnqueueDequeService enqueueDequeService;

    @Override
    public List<CreditDisbursementDTO> getAll() {
        return creditDisbursementRepository.findAll().stream().map(CreditDisbursementEntity::getDto).toList();
    }

    @Override
    public CreditDisbursementDTO add(CreditDisbursementDTO creditDisbursementDTO) {
        log.info("Add credit disbursement {}", creditDisbursementDTO);
        ResponseEntity<AccountDTO> responseEntitiyDepositInAccount = this.accountRESTClient.depositInAccount(
                DepositDTO.builder()
                        .accountNumber(creditDisbursementDTO.getAccountNumber())
                        .amount(creditDisbursementDTO.getAmount())
                        .customerCu(creditDisbursementDTO.getCustomerCu())
                        .build()
        );
        if (responseEntitiyDepositInAccount.getStatusCode().is2xxSuccessful()) {
            log.info("Deposit in account successful");
            CreditDisbursementEntity creditDisbursementEntity = new CreditDisbursementEntity();
            creditDisbursementEntity.setData(creditDisbursementDTO);
            CreditDisbursementEntity savedEntity = creditDisbursementRepository.save(creditDisbursementEntity);
            // send MQ message
            CreditDisbursementEvent creditDisbursementEvent = CreditDisbursementEvent.builder()
                    .accountNumber(creditDisbursementDTO.getAccountNumber())
                    .amount(creditDisbursementDTO.getAmount())
                    .customerCu(creditDisbursementDTO.getCustomerCu())
                    .email("test@email.com") // variable estatica a fines practicas, cambiar luego!!!
                    .build();
            this.enqueueDequeService.publishMessage(creditDisbursementEvent);
            return savedEntity.getDto();
        }
        return CreditDisbursementDTO.builder().build();
    }

    @Override
    public CreditDisbursementDTO update(CreditDisbursementDTO creditDisbursementDTO) {
        return null;
    }

    @Override
    public void delete(CreditDisbursementDTO creditDisbursementDTO) {

    }

    @Override
    public CreditDisbursementDTO getById(String id) {
        return null;
    }
}
