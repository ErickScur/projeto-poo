package com.api.main.factories.usecases.paymentbill;

import com.api.data.protocols.paymentslip.ILoadPaymentBillRepository;
import com.api.data.usecasesimplementation.paymentbill.LoadPaymentBillUseCaseImplementation;
import com.api.domain.usecases.paymentbill.ILoadPaymentBillUseCase;
import com.api.main.factories.repositories.LoadPaymentBillRepositoryFactory;

public class LoadPaymentBillUseCaseFactory {
    public static ILoadPaymentBillUseCase makeLoadPaymentBillUseCase() {
        ILoadPaymentBillRepository loadPaymentBillRepository = LoadPaymentBillRepositoryFactory.makeLoadPaymentBillRepository();

        return new LoadPaymentBillUseCaseImplementation(loadPaymentBillRepository);
    }
}
