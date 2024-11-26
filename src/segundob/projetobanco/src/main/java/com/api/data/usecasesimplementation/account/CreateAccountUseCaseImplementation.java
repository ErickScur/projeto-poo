package com.api.data.usecasesimplementation.account;

import com.api.data.protocols.database.account.*;
import com.api.data.protocols.validators.IAccountValidator;
import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;
import com.api.domain.exceptions.UniqueFieldInUseException;
import com.api.domain.usecases.account.ICreateAccountUseCase;

public class CreateAccountUseCaseImplementation implements ICreateAccountUseCase {
    private final ICreateAccountRepository createAccountRepository;
    private final ILoadAccountByDocumentRepository loadAccountByDocumentRepository;
    private final ILoadAccountByEmailRepository loadAccountByEmailRepository;
    private final IAccountValidator accountValidator;

    public CreateAccountUseCaseImplementation(
            ICreateAccountRepository createAccountRepository,
            ILoadAccountByDocumentRepository loadAccountByDocumentRepository,
            ILoadAccountByEmailRepository loadAccountByEmailRepository,
            IAccountValidator accountValidator
    ) {
        this.createAccountRepository = createAccountRepository;
        this.loadAccountByDocumentRepository = loadAccountByDocumentRepository;
        this.loadAccountByEmailRepository = loadAccountByEmailRepository;
        this.accountValidator = accountValidator;
    }

    @Override
    public Account execute(CreateAccountDTO createAccountDTO) {
        if (this.loadAccountByDocumentRepository.loadAccountByDocument(createAccountDTO.getDocument()) != null) {
            throw new UniqueFieldInUseException("Document already in use");
        }

        if (this.loadAccountByEmailRepository.loadAccountByEmail(createAccountDTO.getEmail()) != null) {
            throw new UniqueFieldInUseException("Email already in use");
        }

        this.accountValidator.validate(createAccountDTO);

        return this.createAccountRepository.createAccount(createAccountDTO);
    }
}
