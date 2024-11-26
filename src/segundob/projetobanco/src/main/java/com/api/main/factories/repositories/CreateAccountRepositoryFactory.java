package com.api.main.factories.repositories;

import com.api.data.protocols.database.account.ICreateAccountRepository;
import com.api.infra.database.inmemory.InMemoryRepositoriesUnitOfWork;
import com.api.infra.database.supabase.repositories.account.SupabaseAccountRepository;

public class CreateAccountRepositoryFactory {
    public static ICreateAccountRepository makeCreateAccountRepository(String databaseType) {
        if ("memory".equalsIgnoreCase(databaseType)) {
            return InMemoryRepositoriesUnitOfWork.getInstance().getCreateAccountRepository();
        } else if ("supabase".equalsIgnoreCase(databaseType)) {
            return new SupabaseAccountRepository();
        }

        return null;
    }
}
