package com.api.infra.database.inmemory;

import com.api.data.protocols.database.account.*;
import com.api.infra.database.inmemory.account.InMemoryAccountRepository;

public class InMemoryRepositoriesUnitOfWork {
    private static InMemoryRepositoriesUnitOfWork instance;

    private final ICreateAccountRepository createAccountRepository;
    private final ILoadAccountByDocumentRepository loadAccountByDocumentRepository;
    private final ILoadAccountByEmailRepository loadAccountByEmailRepository;

    private InMemoryRepositoriesUnitOfWork() {
        InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository();

        this.createAccountRepository = inMemoryAccountRepository;
        this.loadAccountByDocumentRepository = inMemoryAccountRepository;
        this.loadAccountByEmailRepository = inMemoryAccountRepository;
    }

    public static InMemoryRepositoriesUnitOfWork getInstance() {
        if (instance == null) {
            synchronized (InMemoryRepositoriesUnitOfWork.class) {
                if (instance == null) {
                    instance = new InMemoryRepositoriesUnitOfWork();
                }
            }
        }
        return instance;
    }

    public ICreateAccountRepository getCreateAccountRepository() {
        return createAccountRepository;
    }

    public ILoadAccountByDocumentRepository getLoadAccountByDocumentRepository() {
        return loadAccountByDocumentRepository;
    }

    public ILoadAccountByEmailRepository getLoadAccountByEmailRepository() {
        return loadAccountByEmailRepository;
    }
}