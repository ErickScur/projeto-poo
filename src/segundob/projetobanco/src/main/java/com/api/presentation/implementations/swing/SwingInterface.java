package com.api.presentation.implementations.swing;

import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;
import com.api.domain.entities.PaymentBill;
import com.api.domain.usecases.account.ICreateAccountUseCase;
import com.api.domain.usecases.account.ILoginAccountUseCase;
import com.api.domain.usecases.paymentbill.ILoadPaymentBillUseCase;
import com.api.domain.usecases.paymentbill.IPayPaymentBillUseCase;
import com.api.domain.usecases.pix.IGeneratePixQRCodeUseCase;
import com.api.presentation.Presentation;

import javax.swing.*;

public class SwingInterface implements Presentation {
    private final ICreateAccountUseCase createAccountUseCase;
    private final ILoginAccountUseCase loginAccountUseCase;
    private final ILoadPaymentBillUseCase loadPaymentBillUseCase;
    private final IPayPaymentBillUseCase payPaymentBillUseCase;
    private final IGeneratePixQRCodeUseCase generatePixQRCodeUseCase;

    public SwingInterface(
            ICreateAccountUseCase createAccountUseCase,
            ILoginAccountUseCase loginAccountUseCase,
            ILoadPaymentBillUseCase loadPaymentBillUseCase,
            IPayPaymentBillUseCase payPaymentBillUseCase,
            IGeneratePixQRCodeUseCase generatePixQRCodeUseCase
    ) {
        this.createAccountUseCase = createAccountUseCase;
        this.loginAccountUseCase = loginAccountUseCase;
        this.loadPaymentBillUseCase = loadPaymentBillUseCase;
        this.payPaymentBillUseCase = payPaymentBillUseCase;
        this.generatePixQRCodeUseCase = generatePixQRCodeUseCase;
    }

    @Override
    public int showMenuAndGetOption() {
        String[] options = {
                "1 - Criar conta",
                "2 - Acessar conta",
                "3 - Consultar boletos",
                "4 - Pagar boleto",
                "5 - Gerar QR Code Pix",
                "6 - Sair da conta",
                "7 - Sair do sistema"
        };
        String selectedOption = (String) JOptionPane.showInputDialog(
                null,
                "Escolha uma opção:",
                "Menu Principal",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selectedOption == null) return 7;

        return Integer.parseInt(selectedOption.split(" ")[0]);
    }

    @Override
    public Account showRegisterAccount() {
        String document = JOptionPane.showInputDialog("Digite seu documento:");
        String name = JOptionPane.showInputDialog("Digite seu nome:");
        String email = JOptionPane.showInputDialog("Digite seu email:");
        String password = JOptionPane.showInputDialog("Digite sua senha:");

        if (document == null || name == null || email == null || password == null) {
            showErrorMsg("Cadastro cancelado.");
            return null;
        }

        try {
            CreateAccountDTO accountDTO = new CreateAccountDTO(document, name, email, password);
            Account newAccount = createAccountUseCase.execute(accountDTO);
            showSuccessMsg("Conta criada com sucesso!");
            return newAccount;
        } catch (Exception e) {
            showErrorMsg("Erro ao criar conta: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Account showLoginAccount() {
        String email = JOptionPane.showInputDialog("Digite seu email:");
        String password = JOptionPane.showInputDialog("Digite sua senha:");

        if (email == null || password == null) {
            showErrorMsg("Login cancelado.");
            return null;
        }

        try {
            Account account = loginAccountUseCase.execute(email, password);
            if (account != null) {
                showSuccessMsg("Login efetuado com sucesso!");
            } else {
                showErrorMsg("Email ou senha inválidos!");
            }
            return account;
        } catch (Exception e) {
            showErrorMsg("Erro ao acessar conta: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccessMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showAccountInfo(Account account) {
        JOptionPane.showMessageDialog(
                null,
                "Nome: " + account.getName() +
                        "\nDocumento: " + account.getDocument() +
                        "\nEmail: " + account.getEmail(),
                "Informações da Conta",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void loadPaymentBillData() {
        String digitableLine = JOptionPane.showInputDialog("Digite a linha digitável do boleto:");
        if (digitableLine == null) {
            showErrorMsg("Consulta de boleto cancelada.");
            return;
        }

        try {
            PaymentBill paymentBill = loadPaymentBillUseCase.loadPaymentBill(digitableLine);
            if (paymentBill != null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Beneficiário: " + paymentBill.getAssignor() +
                                "\nData de Liquidação: " + paymentBill.getSettleDate() +
                                "\nValor Original: " + paymentBill.getValue(),
                        "Dados do Boleto",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showErrorMsg("Boleto não encontrado.");
            }
        } catch (Exception e) {
            showErrorMsg("Erro ao consultar boleto: " + e.getMessage());
        }
    }

    @Override
    public void payPaymentBill() {
        String digitableLine = JOptionPane.showInputDialog("Digite a linha digitável do boleto:");
        if (digitableLine == null) {
            showErrorMsg("Pagamento de boleto cancelado.");
            return;
        }

        try {
            PaymentBill paymentBill = loadPaymentBillUseCase.loadPaymentBill(digitableLine);
            if (paymentBill == null) {
                showErrorMsg("Boleto não encontrado.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirma o pagamento de:\nBeneficiário: " + paymentBill.getAssignor() +
                            "\nValor: " + paymentBill.getValue(),
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                payPaymentBillUseCase.payPaymentBill(paymentBill.getTransactionId());
                showSuccessMsg("Pagamento realizado com sucesso!");
            }
        } catch (Exception e) {
            showErrorMsg("Erro ao pagar boleto: " + e.getMessage());
        }
    }

    @Override
    public void generatePixQRCode() {
        String amountStr = JOptionPane.showInputDialog("Digite o valor para o QR Code PIX:");
        if (amountStr == null) {
            showErrorMsg("Geração de QR Code cancelada.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            String pixQRCode = generatePixQRCodeUseCase.generatePixQRCode(amount);
            JOptionPane.showMessageDialog(
                    null,
                    pixQRCode,
                    "QR Code PIX Gerado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (NumberFormatException e) {
            showErrorMsg("Valor inválido.");
        } catch (Exception e) {
            showErrorMsg("Erro ao gerar QR Code PIX: " + e.getMessage());
        }
    }
}