package com.api.presentation;

import com.api.domain.entities.Account;

public interface Presentation {
    int showMenuAndGetOption();
    Account showRegisterAccount();
    Account showLoginAccount();
    void showErrorMsg(String msg);
    void showSuccessMsg(String msg);
    void showAccountInfo(Account account);
    void loadPaymentBillData();
    void payPaymentBill();
    void generatePixQRCode();
}
