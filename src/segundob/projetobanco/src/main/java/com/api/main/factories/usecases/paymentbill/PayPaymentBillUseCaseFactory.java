package com.api.main.factories.usecases.paymentbill;

import com.api.data.usecasesimplementation.paymentbill.PayPaymentBillUseCaseImplementation;
import com.api.domain.usecases.paymentbill.IPayPaymentBillUseCase;
import com.api.main.factories.repositories.PayPaymentBillRepositoryFactory;

public class PayPaymentBillUseCaseFactory {
    public static IPayPaymentBillUseCase makePayPaymentBillUseCase() {
        return new PayPaymentBillUseCaseImplementation(PayPaymentBillRepositoryFactory.makePayPaymentBillRepository());
    }
}
