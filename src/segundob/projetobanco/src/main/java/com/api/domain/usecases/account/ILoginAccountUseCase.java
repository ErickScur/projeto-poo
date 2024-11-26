package com.api.domain.usecases.account;

import com.api.domain.entities.Account;

public interface ILoginAccountUseCase {
    Account execute(String email, String password);
}
