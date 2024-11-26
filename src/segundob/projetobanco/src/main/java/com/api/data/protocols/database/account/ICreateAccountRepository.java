package com.api.data.protocols.database.account;

import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;

public interface ICreateAccountRepository {
    Account createAccount(CreateAccountDTO createAccountDTO);
}
