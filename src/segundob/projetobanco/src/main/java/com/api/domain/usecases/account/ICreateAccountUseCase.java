package com.api.domain.usecases.account;

import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;

public interface ICreateAccountUseCase {
    Account execute(CreateAccountDTO createAccountDTO);
}
