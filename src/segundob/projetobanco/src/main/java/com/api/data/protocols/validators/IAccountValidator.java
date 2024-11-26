package com.api.data.protocols.validators;

import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.exceptions.ValidationException;

public interface IAccountValidator {
    void validate(CreateAccountDTO createAccountDTO) throws ValidationException;
}
