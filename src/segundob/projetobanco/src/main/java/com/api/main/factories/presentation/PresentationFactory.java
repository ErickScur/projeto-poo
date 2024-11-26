package com.api.main.factories.presentation;

import com.api.main.factories.usecases.account.*;
import com.api.main.factories.usecases.paymentbill.*;
import com.api.main.factories.usecases.pix.GeneratePixQRCodeUseCaseFactory;
import com.api.presentation.Presentation;
import com.api.presentation.implementations.console.ConsoleInterface;
import com.api.presentation.implementations.swing.SwingInterface;

public class PresentationFactory {

    public static Presentation makePresentation(String presentationType, String databaseType) {
        if ("console".equalsIgnoreCase(presentationType)) {
            return new ConsoleInterface(
                    CreateAccountUseCaseFactory.makeCreateAccountUseCase(databaseType),
                    LoginAccountUseCaseFactory.makeLoginAccountUseCase(databaseType),
                    LoadPaymentBillUseCaseFactory.makeLoadPaymentBillUseCase(),
                    PayPaymentBillUseCaseFactory.makePayPaymentBillUseCase(),
                    GeneratePixQRCodeUseCaseFactory.makeGeneratePixQRCodeUseCase()
            );
        } else if ("swing".equalsIgnoreCase(presentationType)) {
            return new SwingInterface(
                    CreateAccountUseCaseFactory.makeCreateAccountUseCase(databaseType),
                    LoginAccountUseCaseFactory.makeLoginAccountUseCase(databaseType),
                    LoadPaymentBillUseCaseFactory.makeLoadPaymentBillUseCase(),
                    PayPaymentBillUseCaseFactory.makePayPaymentBillUseCase(),
                    GeneratePixQRCodeUseCaseFactory.makeGeneratePixQRCodeUseCase()
            );
        }

        throw new IllegalArgumentException("Invalid presentation presentationType: " + presentationType);
    }
}
