package com.api.main.factories.repositories;

import com.api.data.protocols.database.account.ILoadAccountByDocumentRepository;
import com.api.infra.database.inmemory.InMemoryRepositoriesUnitOfWork;
import com.api.infra.database.supabase.repositories.account.SupabaseAccountRepository;

public class LoadAccountByDocumentRepositoryFactory {
    public static ILoadAccountByDocumentRepository makeLoadAccountByDocumentRepository(String databaseType) {
        if ("memory".equalsIgnoreCase(databaseType)) {
            return InMemoryRepositoriesUnitOfWork.getInstance().getLoadAccountByDocumentRepository();
        } else if ("supabase".equalsIgnoreCase(databaseType)) {
            return new SupabaseAccountRepository();
        }

        return null;
    }
}
