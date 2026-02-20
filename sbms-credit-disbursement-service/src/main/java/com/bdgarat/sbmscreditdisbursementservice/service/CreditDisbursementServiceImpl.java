package com.bdgarat.sbmscreditdisbursementservice.service;

import com.bdgarat.sbmscreditdisbursementservice.client.IAccountRESTClient;
import com.bdgarat.sbmscreditdisbursementservice.config.EnqueueDequeService;
import com.bdgarat.sbmscreditdisbursementservice.dto.AccountDTO;
import com.bdgarat.sbmscreditdisbursementservice.dto.CreditDisbursementDTO;
import com.bdgarat.sbmscreditdisbursementservice.dto.DepositDTO;
import com.bdgarat.sbmscreditdisbursementservice.entity.CreditDisbursementEntity;
import com.bdgarat.sbmscreditdisbursementservice.event.CreditDisbursementEvent;
import com.bdgarat.sbmscreditdisbursementservice.mappers.CreditDisbursementMapper;
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
    private final CreditDisbursementMapper creditDisbursementMapper;

    @Override
    public List<CreditDisbursementDTO> getAll() {
        return creditDisbursementRepository.findAll()
                .stream()
                .map(creditDisbursementMapper::toDto)
                .toList();
    }

    @Override
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
    public void delete(CreditDisbursementDTO creditDisbursementDTO) {

    }

    @Override
    public CreditDisbursementDTO getById(String id) {
        return null;
    }
}
