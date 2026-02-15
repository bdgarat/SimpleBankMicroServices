package com.bdgarat.sbmsaccountservice.service;

import com.bdgarat.sbmsaccountservice.dto.AccountDTO;
import com.bdgarat.sbmsaccountservice.dto.DepositDTO;
import com.bdgarat.sbmsaccountservice.util.ICrud;

public interface IAccountService extends ICrud<AccountDTO> {

    public AccountDTO depositInAccount(DepositDTO depositDTO);
}
