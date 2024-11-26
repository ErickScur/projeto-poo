package com.api.data.protocols.database.account;

import com.api.domain.entities.Account;

public interface ILoadAccountByDocumentRepository {
    Account loadAccountByDocument(String document);
}
