package com.api.main.factories.validators;

import com.api.data.protocols.validators.IAccountValidator;
import com.api.infra.validators.AccountValidator;

public class AccountValidatorFactory {
    public static IAccountValidator makeAccountValidator() {
        return new AccountValidator();
    }
}
