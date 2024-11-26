package com.api.main.factories.repositories;

import com.api.data.protocols.paymentslip.IPayPaymentBillRepository;
import com.api.infra.api.CellcoinAPIRepository;

public class PayPaymentBillRepositoryFactory {
    public static IPayPaymentBillRepository makePayPaymentBillRepository() {
        return new CellcoinAPIRepository();
    }
}
