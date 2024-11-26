package com.api.infra.database.inmemory.account;

import com.api.data.protocols.database.account.*;
import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InMemoryAccountRepository implements ICreateAccountRepository, ILoadAccountByEmailRepository, ILoadAccountByDocumentRepository {
    private final List<Account> accounts;

    public InMemoryAccountRepository() {
        this.accounts = new ArrayList<>();
    }

    @Override
    public Account createAccount(CreateAccountDTO createAccountDTO) {
        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        String accountNumber = this.generateAccountNumber();

        Account account = new Account(id, createAccountDTO.getDocument(), createAccountDTO.getName(), createAccountDTO.getEmail(),  accountNumber, createAccountDTO.getPassword(), createdAt, null);

        this.accounts.add(account);
        return account;
    }

    @Override
    public Account loadAccountByEmail(String email) {
        for (Account account : this.accounts) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }

        return null;
    }

    @Override
    public Account loadAccountByDocument(String document) {
        for (Account account : this.accounts) {
            if (account.getDocument().equals(document)) {
                return account;
            }
        }

        return null;
    }

    private String generateAccountNumber() {
        int agencyNumber = ThreadLocalRandom.current().nextInt(1000, 9999);
        int accountBase = ThreadLocalRandom.current().nextInt(100000, 999999);

        int checkDigit = (agencyNumber + accountBase) % 10;

        return String.format("%04d-%06d-%d", agencyNumber, accountBase, checkDigit);
    }
}
