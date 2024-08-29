package primeirob.projeto2.conversormoedas.domain.usecases;

import primeirob.projeto2.conversormoedas.domain.entities.ExchangeRate;

public interface LoadCurrencyRateUseCase {
    ExchangeRate loadCurrencyRate(String baseCurrency, String targetCurrency);
}
