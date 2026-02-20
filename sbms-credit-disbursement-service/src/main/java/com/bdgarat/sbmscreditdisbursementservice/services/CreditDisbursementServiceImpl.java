package com.bdgarat.sbmscreditdisbursementservice.services;

import com.bdgarat.sbmscreditdisbursementservice.clients.IAccountRESTClient;
import com.bdgarat.sbmscreditdisbursementservice.config.EnqueueDequeService;
import com.bdgarat.sbmscreditdisbursementservice.dtos.AccountDTO;
import com.bdgarat.sbmscreditdisbursementservice.dtos.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.dtos.DepositDTO;
import com.bdgarat.sbmscreditdisbursementservice.entities.CreditDisbursementEntity;
import com.bdgarat.sbmscreditdisbursementservice.events.CreditDisbursementEvent;
import com.bdgarat.sbmscreditdisbursementservice.mappers.CreditDisbursementMapper;
import com.bdgarat.sbmscreditdisbursementservice.repositories.ICreditDisbursementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class CreditDisbursementServiceImpl implements ICreditDisbursementService {

    private final ICreditDisbursementRepository creditDisbursementRepository;
    private final IAccountRESTClient accountRESTClient;
    private final EnqueueDequeService enqueueDequeService;
    private final CreditDisbursementMapper creditDisbursementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CreditDisbursementDTO> getAll() {
        return creditDisbursementRepository.findAll()
                .stream()
                .map(creditDisbursementMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CreditDisbursementDTO add(CreditDisbursementDTO creditDisbursementDTO) {
        log.info("Add credit disbursement {}", creditDisbursementDTO);
        ResponseEntity<AccountDTO> responseEntitiyDepositInAccount = this.accountRESTClient.depositInAccount(
                DepositDTO.builder()
                        .accountNumber(creditDisbursementDTO.accountNumber())
                        .amount(creditDisbursementDTO.amount())
                        .customerCu(creditDisbursementDTO.customerCu())
                        .build()
        );
        if (responseEntitiyDepositInAccount.getStatusCode().is2xxSuccessful()) {
            log.info("Deposit in account successful");
            CreditDisbursementEntity creditDisbursementEntity = creditDisbursementMapper.toEntity(creditDisbursementDTO);
            CreditDisbursementEntity savedEntity = creditDisbursementRepository.save(creditDisbursementEntity);
            // send MQ message
            CreditDisbursementEvent creditDisbursementEvent = CreditDisbursementEvent.builder()
                    .accountNumber(creditDisbursementDTO.accountNumber())
                    .amount(creditDisbursementDTO.amount())
                    .customerCu(creditDisbursementDTO.customerCu())
                    .email("garat.braian@gmail.com") // hardcode de mail solo a fines practicos, por limitacion de mailtrap. Cambiar luego!!!
                    .build();
            this.enqueueDequeService.publishMessage(creditDisbursementEvent);
            return creditDisbursementMapper.toDto(savedEntity);
        }
        return CreditDisbursementDTO.builder().build();
    }

    @Override
    public CreditDisbursementDTO update(CreditDisbursementDTO creditDisbursementDTO) {
        return null;
    }

    @Override
    public void delete(CreditDisbursementDTO creditDisbursementDTO) {}

    @Override
    @Transactional(readOnly = true)
    public CreditDisbursementDTO getById(String id) {
        log.info("Get credit disbursement by id {}", id);
        return creditDisbursementRepository.findById(id)
                .map(creditDisbursementMapper::toDto)
                .orElse(null);
    }


}
