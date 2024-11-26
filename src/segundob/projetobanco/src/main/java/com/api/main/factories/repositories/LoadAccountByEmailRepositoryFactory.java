package com.api.main.factories.repositories;

import com.api.data.protocols.database.account.ILoadAccountByEmailRepository;
import com.api.infra.database.inmemory.InMemoryRepositoriesUnitOfWork;
import com.api.infra.database.supabase.repositories.account.SupabaseAccountRepository;

public class LoadAccountByEmailRepositoryFactory {
    public static ILoadAccountByEmailRepository makeLoadAccountByEmailRepository(String databaseType) {
        if ("memory".equalsIgnoreCase(databaseType)) {
            return InMemoryRepositoriesUnitOfWork.getInstance().getLoadAccountByEmailRepository();
        } else if ("supabase".equalsIgnoreCase(databaseType)) {
            return new SupabaseAccountRepository();
        }

        return null;
    }
}
