package com.api.data.protocols.paymentslip;

import com.api.domain.entities.PaymentBill;

public interface ILoadPaymentBillRepository {
    PaymentBill loadPaymentBill(String digitableLine);
}
