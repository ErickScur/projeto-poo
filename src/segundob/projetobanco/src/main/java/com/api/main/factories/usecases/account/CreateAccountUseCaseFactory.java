package com.api.main.factories.usecases.account;

import com.api.data.protocols.validators.IAccountValidator;
import com.api.data.usecasesimplementation.account.CreateAccountUseCaseImplementation;
import com.api.domain.usecases.account.ICreateAccountUseCase;
import com.api.data.protocols.database.account.*;
import com.api.main.factories.repositories.*;
import com.api.main.factories.validators.AccountValidatorFactory;

public class CreateAccountUseCaseFactory {
    public static ICreateAccountUseCase makeCreateAccountUseCase(String databaseType) {
        ICreateAccountRepository createAccountRepository = CreateAccountRepositoryFactory.makeCreateAccountRepository(databaseType);
        ILoadAccountByDocumentRepository loadAccountByDocumentRepository = LoadAccountByDocumentRepositoryFactory.makeLoadAccountByDocumentRepository(databaseType);
        ILoadAccountByEmailRepository loadAccountByEmailRepository = LoadAccountByEmailRepositoryFactory.makeLoadAccountByEmailRepository(databaseType);
        IAccountValidator accountValidator = AccountValidatorFactory.makeAccountValidator();
        return new CreateAccountUseCaseImplementation(createAccountRepository, loadAccountByDocumentRepository, loadAccountByEmailRepository, accountValidator);
    }
}
