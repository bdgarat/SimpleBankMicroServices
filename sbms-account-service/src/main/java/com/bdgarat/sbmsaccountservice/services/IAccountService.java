package com.bdgarat.sbmsaccountservice.services;

import com.bdgarat.sbmsaccountservice.dtos.AccountDTO;
import com.bdgarat.sbmsaccountservice.dtos.DepositDTO;
import com.bdgarat.sbmsaccountservice.utils.ICrud;

public interface IAccountService extends ICrud<AccountDTO> {

    public AccountDTO depositInAccount(DepositDTO depositDTO);
}
