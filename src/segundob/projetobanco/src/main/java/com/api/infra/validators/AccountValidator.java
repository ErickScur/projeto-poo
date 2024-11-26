package com.api.infra.validators;

import com.api.data.protocols.validators.IAccountValidator;
import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.exceptions.ValidationException;

public class AccountValidator implements IAccountValidator {

    @Override
    public void validate(CreateAccountDTO createAccountDTO) throws ValidationException {
        if (createAccountDTO == null) {
            throw new ValidationException("Os dados da conta não podem ser nulos");
        }

        validatePassword(createAccountDTO.getPassword());
        validateDocument(createAccountDTO.getDocument());
        validateName(createAccountDTO.getName());
        validateEmail(createAccountDTO.getEmail());
    }

    private void validatePassword(Object password) {
        if (password == null) {
            throw new ValidationException("A senha não pode estar em branco");
        }

        if (!(password instanceof String passwordStr)) {
            throw new ValidationException("A senha deve ser uma string");
        }

        if (passwordStr.length() < 6 || passwordStr.length() > 20) {
            throw new ValidationException("A senha deve ter entre 6 e 20 caracteres");
        }
    }

    private void validateDocument(Object document) {
        if (document == null) {
            throw new ValidationException("O documento não pode estar em branco");
        }

        if (!(document instanceof String documentStr)) {
            throw new ValidationException("O documento deve ser uma string");
        }

        if (documentStr.length() == 11 && isValidCPF(documentStr)) {
            return;
        } else if (documentStr.length() == 14 && isValidCNPJ(documentStr)) {
            return;
        } else {
            throw new ValidationException("O documento deve ser um CPF ou CNPJ válido");
        }
    }

    private void validateName(Object name) {
        if (name == null) {
            throw new ValidationException("O nome não pode estar em branco");
        }

        if (!(name instanceof String nameStr)) {
            throw new ValidationException("O nome deve ser uma string");
        }

        if (nameStr.length() < 2 || nameStr.length() > 50) {
            throw new ValidationException("O nome deve ter entre 2 e 50 caracteres");
        }
    }

    private void validateEmail(Object email) {
        if (email == null) {
            throw new ValidationException("O email não pode estar em branco");
        }

        if (!(email instanceof String emailStr)) {
            throw new ValidationException("O email deve ser uma string");
        }

        if (!emailStr.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email deve ser válido");
        }
    }

    private boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * pesos[i];
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        pesos = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * pesos[i];
        }

        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        return segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int[] pesos = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }

        pesos = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }

        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        return segundoDigito == Character.getNumericValue(cnpj.charAt(13));
    }
}
