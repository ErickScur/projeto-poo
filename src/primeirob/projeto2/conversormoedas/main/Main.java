package primeirob.projeto2.conversormoedas.main;

import primeirob.projeto2.conversormoedas.main.factories.ExchangeRatesConsoleInterfaceFactory;
import primeirob.projeto2.conversormoedas.presentation.ExchangeRatesConsoleInterface;

public class Main {
    public static void main(String[] args) {
        ExchangeRatesConsoleInterface exchangeRatesConsoleInterface = ExchangeRatesConsoleInterfaceFactory.makeExchangeRatesConsoleInterface();
        exchangeRatesConsoleInterface.start();
    }
}

