package com.api.data.protocols.database.account;

import com.api.domain.entities.Account;

public interface ILoadAccountByEmailRepository {
    public Account loadAccountByEmail(String email);
}
