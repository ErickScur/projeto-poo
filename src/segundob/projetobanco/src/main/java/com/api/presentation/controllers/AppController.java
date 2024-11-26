package com.api.presentation.controllers;

import com.api.domain.entities.Account;
import com.api.presentation.Presentation;

public class AppController {
    private final Presentation presentation;
    private Account loggedAccount;

    public AppController(Presentation presentation) {
        this.presentation = presentation;
        this.loggedAccount = null;
    }

    public void start() {
        while (true) {
            int option = presentation.showMenuAndGetOption();
            switch (option) {
                case 1:
                    if (loggedAccount != null) {
                        presentation.showErrorMsg("Você já está logado!");
                        break;
                    }

                    Account newAccount = presentation.showRegisterAccount();
                    if (newAccount != null) {
                        presentation.showSuccessMsg("Bem-vindo, " + newAccount.getName() + "!");
                        this.loggedAccount = newAccount;
                    }

                    break;
                case 2:
                    if (this.loggedAccount != null) {
                        presentation.showErrorMsg("Você já está logado!");
                        break;
                    }

                    Account account = presentation.showLoginAccount();
                    if (account != null) {
                        presentation.showSuccessMsg("Bem-vindo, " + account.getName() + "!");
                        this.loggedAccount = account;
                        presentation.showAccountInfo(loggedAccount);
                    }

                    break;
                case 3:
                    if (this.loggedAccount == null) {
                        presentation.showErrorMsg("Você precisa estar logado para consultar boletos!");
                        break;
                    }

                    presentation.loadPaymentBillData();

                    break;
                case 4:
                    if (this.loggedAccount == null) {
                        presentation.showErrorMsg("Você precisa estar logado para pagar boletos!");
                        break;
                    }

                    presentation.payPaymentBill();

                    break;
                case 5:
                    if (this.loggedAccount == null) {
                        presentation.showErrorMsg("Você precisa estar logado para consultar saldo!");
                        break;
                    }

                    presentation.generatePixQRCode();

                    break;
                case 6:
                    presentation.showSuccessMsg("Saindo da conta");
                    this.loggedAccount = null;

                    break;
                case 7:
                    presentation.showSuccessMsg("Fechando sistema");
                    return;
                default:
                    presentation.showErrorMsg("Opção inválida!");
            }
        }
    }
}