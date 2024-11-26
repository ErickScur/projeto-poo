package com.api.main.factories.usecases.account;

import com.api.data.protocols.database.account.ILoadAccountByEmailRepository;
import com.api.data.usecasesimplementation.account.LoginAccountUseCaseImplementation;
import com.api.domain.usecases.account.ILoginAccountUseCase;
import com.api.main.factories.repositories.LoadAccountByEmailRepositoryFactory;

public class LoginAccountUseCaseFactory {
    public static ILoginAccountUseCase makeLoginAccountUseCase(String databaseType) {
        ILoadAccountByEmailRepository loadAccountByEmailRepository = LoadAccountByEmailRepositoryFactory.makeLoadAccountByEmailRepository(databaseType);
        return new LoginAccountUseCaseImplementation(loadAccountByEmailRepository);
    }
}
