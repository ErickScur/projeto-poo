package com.api.main.factories.repositories;

import com.api.data.protocols.paymentslip.ILoadPaymentBillRepository;
import com.api.infra.api.CellcoinAPIRepository;

public class LoadPaymentBillRepositoryFactory {
    public static ILoadPaymentBillRepository makeLoadPaymentBillRepository() {
        return new CellcoinAPIRepository();
    }
}
