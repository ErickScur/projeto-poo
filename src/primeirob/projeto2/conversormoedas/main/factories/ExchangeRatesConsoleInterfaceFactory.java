package primeirob.projeto2.conversormoedas.main.factories;

import primeirob.projeto2.conversormoedas.data.protocols.LoadAvailableSymbolsRepository;
import primeirob.projeto2.conversormoedas.data.protocols.LoadCurrencyExchangeRateRepository;
import primeirob.projeto2.conversormoedas.data.usecasesimplementation.LoadAvailableSymbolsUseCaseImplementation;
import primeirob.projeto2.conversormoedas.data.usecasesimplementation.LoadCurrencyRateUseCaseImplementation;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadAvailableSymbolsUseCase;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadCurrencyRateUseCase;
import primeirob.projeto2.conversormoedas.infra.exchangeratesapi.ExchangeRatesApiRepository;
import primeirob.projeto2.conversormoedas.presentation.ExchangeRatesConsoleInterface;

public class ExchangeRatesConsoleInterfaceFactory {
    public static ExchangeRatesConsoleInterface makeExchangeRatesConsoleInterface() {
        LoadCurrencyExchangeRateRepository loadCurrencyExchangeRateRepository = new ExchangeRatesApiRepository();
        LoadAvailableSymbolsRepository loadAvailableSymbolsRepository = new ExchangeRatesApiRepository();

        LoadCurrencyRateUseCase loadCurrencyRateUseCase = new LoadCurrencyRateUseCaseImplementation(
                loadCurrencyExchangeRateRepository
        );
        LoadAvailableSymbolsUseCase loadAvailableSymbolsUseCase = new LoadAvailableSymbolsUseCaseImplementation(
                loadAvailableSymbolsRepository
        );

        return new ExchangeRatesConsoleInterface(loadAvailableSymbolsUseCase, loadCurrencyRateUseCase);
    }
}
