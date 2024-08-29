package primeirob.projeto2.conversormoedas.data.protocols;

import primeirob.projeto2.conversormoedas.domain.entities.ExchangeRate;

public interface LoadCurrencyExchangeRateRepository {
    ExchangeRate loadCurrencyExchangeRate(String baseCurrency, String targetCurrency);
}
