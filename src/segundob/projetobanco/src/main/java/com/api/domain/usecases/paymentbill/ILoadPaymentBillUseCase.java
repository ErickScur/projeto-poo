package com.api.domain.usecases.paymentbill;

import com.api.domain.entities.PaymentBill;

public interface ILoadPaymentBillUseCase {
    PaymentBill loadPaymentBill(String digitableLine);
}
