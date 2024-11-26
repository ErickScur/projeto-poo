package com.api.data.usecasesimplementation.paymentbill;

import com.api.data.protocols.paymentslip.IPayPaymentBillRepository;
import com.api.domain.usecases.paymentbill.IPayPaymentBillUseCase;

public class PayPaymentBillUseCaseImplementation implements IPayPaymentBillUseCase {
    private IPayPaymentBillRepository payPaymentBillRepository;

    public PayPaymentBillUseCaseImplementation(IPayPaymentBillRepository payPaymentBillRepository) {
        this.payPaymentBillRepository = payPaymentBillRepository;
    }

    @Override
    public void payPaymentBill(Long transactionId) {
        payPaymentBillRepository.payPaymentBill(transactionId);
    }
}
