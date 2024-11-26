package com.api.presentation.implementations.console;

import com.api.domain.dto.account.CreateAccountDTO;
import com.api.domain.entities.Account;
import com.api.domain.entities.PaymentBill;
import com.api.domain.exceptions.UniqueFieldInUseException;
import com.api.domain.exceptions.ValidationException;
import com.api.domain.usecases.account.ICreateAccountUseCase;
import com.api.domain.usecases.account.ILoginAccountUseCase;
import com.api.domain.usecases.paymentbill.ILoadPaymentBillUseCase;
import com.api.domain.usecases.paymentbill.IPayPaymentBillUseCase;
import com.api.domain.usecases.pix.IGeneratePixQRCodeUseCase;
import com.api.presentation.Presentation;

import java.util.Scanner;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ConsoleInterface implements Presentation {
    private static final Scanner scanner = new Scanner(System.in);

    private final ICreateAccountUseCase createAccountUseCase;
    private final ILoginAccountUseCase loginAccountUseCase;
    private final ILoadPaymentBillUseCase loadPaymentBillUseCase;
    private final IPayPaymentBillUseCase payPaymentBillUseCase;
    private final IGeneratePixQRCodeUseCase generatePixQRCodeUseCase;

    public ConsoleInterface(
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
        int option;
        while (true) {
            try {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Criar conta");
                System.out.println("2 - Acessar conta");
                System.out.println("3 - Consultar boletos");
                System.out.println("4 - Pagar boleto");
                System.out.println("5 - Gerar QR Code Pix");
                System.out.println("6 - Sair da conta");
                System.out.println("7 - Sair do sistema");
                System.out.print("Digite sua opção: ");

                option = Integer.parseInt(scanner.nextLine());

                if (option >= 1 && option <= 7) {
                    break;
                } else {
                    System.out.println("Opção inválida! Tente novamente.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.\n");
            }
        }

        return option;
    }

    @Override
    public Account showRegisterAccount() {
        System.out.println("\n=== Cadastro de Conta ===");

        System.out.print("Digite seu documento: ");
        String document = scanner.nextLine();

        System.out.print("Digite seu nome: ");
        String name = scanner.nextLine();

        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String password = scanner.nextLine();

        CreateAccountDTO accountDTO = new CreateAccountDTO(document, name, email, password);

        try {
            Account newAccount = createAccountUseCase.execute(accountDTO);

            showSuccessMsg("Conta criada com sucesso!");

            return newAccount;
        } catch (UniqueFieldInUseException | ValidationException e) {
            showErrorMsg("Erro: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Account showLoginAccount() {
        System.out.println("\n=== Acesso à Conta ===");

        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String password = scanner.nextLine();

        Account account = loginAccountUseCase.execute(email, password);

        if (account == null) {
            showErrorMsg("Email ou senha inválidos!");
        } else {
            showSuccessMsg("Login efetuado com sucesso!");
        }

        return account;
    }

    @Override
    public void showErrorMsg(String msg) {
        System.out.println("\u001B[31m[ERRO]\u001B[0m " + msg);
    }

    @Override
    public void showSuccessMsg(String msg) {
        System.out.println("\u001B[32m[SUCESSO]\u001B[0m " + msg);
    }

    @Override
    public void showAccountInfo(Account account) {
        System.out.println("\n=== Informações da Conta ===");
        System.out.println("Nome: " + account.getName());
        System.out.println("Documento: " + account.getDocument());
        System.out.println("Email: " + account.getEmail());
    }

    @Override
    public void loadPaymentBillData() {
        System.out.println("\n=== Consulta de Boletos ===");

        try {
            System.out.print("Digite a linha digitável do boleto: ");
            String digitableLine = scanner.nextLine();

            if (digitableLine == null || digitableLine.trim().isEmpty()) {
                System.out.println("Linha digitável não pode estar vazia. Tente novamente.");
                return;
            }

            PaymentBill paymentBill = loadPaymentBillUseCase.loadPaymentBill(digitableLine);

            if (paymentBill != null) {
                System.out.println("\n--- Dados do Boleto ---");
                System.out.println("Beneficiário: " + (paymentBill.getAssignor() != null ? paymentBill.getAssignor() : "N/A"));
                System.out.println("Data de Liquidação: " + (paymentBill.getSettleDate() != null ? paymentBill.getSettleDate() : "N/A"));
                System.out.println("Data de Vencimento: " + (paymentBill.getDueDate() != null ? paymentBill.getDueDate() : "N/A"));
                System.out.println("Valor Original: " + (paymentBill.getValue() != null ? "R$ " + paymentBill.getValue() : "N/A"));
                System.out.println("Erro Código: " + (paymentBill.getErrorCode() != null ? paymentBill.getErrorCode() : "N/A"));
                System.out.println("Mensagem: " + (paymentBill.getMessage() != null ? paymentBill.getMessage() : "N/A"));
                System.out.println("------------------------");
            } else {
                System.out.println("Não foi possível encontrar os dados do boleto. Verifique a linha digitável e tente novamente.");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao consultar boleto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado. Por favor, tente novamente.");
        }
    }

    @Override
    public void payPaymentBill() {
        System.out.println("\n=== Pagamento de Boletos ===");

        try {
            System.out.print("Digite a linha digitável do boleto: ");
            String digitableLine = scanner.nextLine();

            if (digitableLine == null || digitableLine.trim().isEmpty()) {
                System.out.println("Linha digitável não pode estar vazia. Tente novamente.");
                return;
            }

            PaymentBill paymentBill = loadPaymentBillUseCase.loadPaymentBill(digitableLine);

            if (paymentBill == null) {
                System.out.println("Não foi possível encontrar os dados do boleto. Verifique a linha digitável e tente novamente.");
                return;
            }

            System.out.println("\n--- Dados do Boleto ---");
            System.out.println("Beneficiário: " + (paymentBill.getAssignor() != null ? paymentBill.getAssignor() : "N/A"));
            System.out.println("Data de Liquidação: " + (paymentBill.getSettleDate() != null ? paymentBill.getSettleDate() : "N/A"));
            System.out.println("Valor Original: " + (paymentBill.getValue() != null ? "R$ " + paymentBill.getValue() : "N/A"));
            System.out.println("------------------------");

            System.out.print("Deseja confirmar o pagamento? (s/n): ");
            String confirmation = scanner.nextLine();

            if (!"s".equalsIgnoreCase(confirmation)) {
                System.out.println("Pagamento cancelado pelo usuário.");
                return;
            }

            this.payPaymentBillUseCase.payPaymentBill(paymentBill.getTransactionId());

            System.out.println("Pagamento realizado com sucesso! ✅");
        } catch (RuntimeException e) {
            System.out.println("Erro ao processar o pagamento: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado. Por favor, tente novamente.");
        }
    }

    public void generatePixQRCode() {
        System.out.println("\n=== Gerar QR Code Pix ===");

        try {
            System.out.print("Digite o valor para o QR Code Pix: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                System.out.println("O valor deve ser maior que zero. Tente novamente.");
                return;
            }

            String pixQRCode = this.generatePixQRCodeUseCase.generatePixQRCode(amount);

            if (pixQRCode == null || pixQRCode.trim().isEmpty()) {
                System.out.println("Erro ao gerar o QR Code Pix. Tente novamente.");
                return;
            }

            System.out.println("\n--- QR Code Pix Gerado ---");
            System.out.println(pixQRCode);
            System.out.println("--------------------------");

            renderQRCodeSwing(pixQRCode);

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Insira um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao gerar QR Code Pix: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado. Por favor, tente novamente.");
        }
    }

    private void renderQRCodeSwing(String pixQRCode) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    pixQRCode, BarcodeFormat.QR_CODE, 300, 300);

            JFrame frame = getjFrame(bitMatrix);
            frame.pack();
            frame.setVisible(true);

        } catch (WriterException e) {
            System.out.println("Erro ao renderizar QR Code: " + e.getMessage());
        }
    }

    private static JFrame getjFrame(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("QR Code Pix");
        frame.add(label);
        return frame;
    }
}
