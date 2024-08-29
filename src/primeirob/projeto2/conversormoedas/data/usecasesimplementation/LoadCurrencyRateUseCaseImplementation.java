package primeirob.projeto2.conversormoedas.data.usecasesimplementation;

import primeirob.projeto2.conversormoedas.data.protocols.LoadCurrencyExchangeRateRepository;
import primeirob.projeto2.conversormoedas.domain.entities.ExchangeRate;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadCurrencyRateUseCase;

public class LoadCurrencyRateUseCaseImplementation implements LoadCurrencyRateUseCase {
    private LoadCurrencyExchangeRateRepository repository;

    public LoadCurrencyRateUseCaseImplementation(LoadCurrencyExchangeRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExchangeRate loadCurrencyRate(String baseCurrency, String targetCurrency) {
        return repository.loadCurrencyExchangeRate(baseCurrency, targetCurrency);
    }
}
