package com.api.domain.dto.account;

public class CreateAccountDTO {
    private String document;
    private String name;
    private String email;
    private String password;

    public CreateAccountDTO(String document, String name, String email, String password) {
        this.document = document;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

}
