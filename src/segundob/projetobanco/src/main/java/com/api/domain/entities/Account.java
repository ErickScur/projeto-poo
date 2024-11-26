package com.api.domain.entities;

import java.time.LocalDateTime;

public class Account {
    private final String id;
    private final String document;
    private final String name;
    private final String email;
    private final String accountNumber;
    private final String password;
    private final LocalDateTime createdAt;
    private LocalDateTime disabledAt;

    public Account(String id, String document, String name, String email, String accountNumber, String password, LocalDateTime createdAt, LocalDateTime disabledAt) {
        this.id = id;
        this.document = document;
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
        this.password = password;
        this.createdAt = createdAt;
        this.disabledAt = disabledAt;
    }

    public String getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDisabledAt() {
        return disabledAt;
    }

    public void disableAccount() {
        this.disabledAt = LocalDateTime.now();
    }

    public boolean isDisabled() {
        return this.disabledAt != null;
    }

    public boolean isActive() {
        return !isDisabled();
    }
}
