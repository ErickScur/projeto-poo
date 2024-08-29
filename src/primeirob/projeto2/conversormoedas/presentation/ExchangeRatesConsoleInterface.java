package primeirob.projeto2.conversormoedas.presentation;

import primeirob.projeto2.conversormoedas.domain.entities.ExchangeRate;
import primeirob.projeto2.conversormoedas.domain.exceptions.InvalidCurrencyException;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadAvailableSymbolsUseCase;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadCurrencyRateUseCase;

import java.util.List;
import java.util.Scanner;

public class ExchangeRatesConsoleInterface {
    private final LoadAvailableSymbolsUseCase loadAvailableSymbolsUseCase;
    private final LoadCurrencyRateUseCase loadCurrencyRateUseCase;

    public ExchangeRatesConsoleInterface(LoadAvailableSymbolsUseCase loadAvailableSymbolsUseCase, LoadCurrencyRateUseCase loadCurrencyRateUseCase) {
        this.loadAvailableSymbolsUseCase = loadAvailableSymbolsUseCase;
        this.loadCurrencyRateUseCase = loadCurrencyRateUseCase;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Conversor de Moedas ---");
            System.out.println("1 - Consultar Moedas");
            System.out.println("2 - Converter Moeda");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    consultarMoedas();
                    break;
                case 2:
                    converterMoeda(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void consultarMoedas() {
        List<String> symbols = loadAvailableSymbolsUseCase.loadAvailableSymbols();
        System.out.println("\nMoedas Disponíveis:");
        for (String symbol : symbols) {
            System.out.println(symbol);
        }
    }

    private void converterMoeda(Scanner scanner) {
        System.out.print("Informe a moeda base: ");
        String baseCurrency = scanner.nextLine().toUpperCase();
        System.out.print("Informe a moeda alvo: ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        try {
            ExchangeRate exchangeRate = loadCurrencyRateUseCase.loadCurrencyRate(baseCurrency, targetCurrency);

            if (exchangeRate.getRate() != -1) {
                System.out.printf("Taxa de câmbio de %s para %s: %.4f%n", baseCurrency, targetCurrency, exchangeRate.getRate());
            } else {
                System.out.println("Conversão falhou. Verifique se as moedas estão corretas.");
            }
        } catch (InvalidCurrencyException e) {
            System.out.println(e.getMessage());
        }
    }
}