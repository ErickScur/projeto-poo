package com.api.data.usecasesimplementation.paymentbill;

import com.api.data.protocols.paymentslip.ILoadPaymentBillRepository;
import com.api.domain.entities.PaymentBill;
import com.api.domain.usecases.paymentbill.ILoadPaymentBillUseCase;

public class LoadPaymentBillUseCaseImplementation implements ILoadPaymentBillUseCase {
    private final ILoadPaymentBillRepository loadPaymentBillRepository;

    public LoadPaymentBillUseCaseImplementation(ILoadPaymentBillRepository loadPaymentBillRepository) {
        this.loadPaymentBillRepository = loadPaymentBillRepository;
    }

    @Override
    public PaymentBill loadPaymentBill(String digitableLine) {
        return this.loadPaymentBillRepository.loadPaymentBill(digitableLine);
    }
}
