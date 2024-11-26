package com.api.data.usecasesimplementation.account;

import com.api.data.protocols.database.account.ILoadAccountByEmailRepository;
import com.api.domain.entities.Account;
import com.api.domain.usecases.account.ILoginAccountUseCase;

public class LoginAccountUseCaseImplementation implements ILoginAccountUseCase {
    private final ILoadAccountByEmailRepository loadAccountByEmailRepository;

    public LoginAccountUseCaseImplementation(ILoadAccountByEmailRepository loadAccountByEmailRepository) {
        this.loadAccountByEmailRepository = loadAccountByEmailRepository;
    }

    @Override
    public Account execute(String email, String password) {
        Account account = this.loadAccountByEmailRepository.loadAccountByEmail(email);

        if (account == null) {
            return null;
        }

        if (!account.getPassword().equals(password)) {
            return null;
        }

        return account;
    }
}
