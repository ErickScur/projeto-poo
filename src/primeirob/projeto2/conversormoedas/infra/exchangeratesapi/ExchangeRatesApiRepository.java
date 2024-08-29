package primeirob.projeto2.conversormoedas.infra.exchangeratesapi;

import primeirob.projeto2.conversormoedas.data.protocols.LoadAvailableSymbolsRepository;
import primeirob.projeto2.conversormoedas.data.protocols.LoadCurrencyExchangeRateRepository;
import primeirob.projeto2.conversormoedas.domain.entities.ExchangeRate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import primeirob.projeto2.conversormoedas.domain.exceptions.InvalidCurrencyException;

public class ExchangeRatesApiRepository implements LoadAvailableSymbolsRepository, LoadCurrencyExchangeRateRepository {
    private static final String API_KEY = "fca_live_so8paSI6MQ5KzeEvRAfUfONe0G7RP8FNcnd8AEA4";

    @Override
    public List<String> loadAvailableSymbols() {
        List<String> symbols = new ArrayList<>();
        try {
            URL url = new URL("https://api.freecurrencyapi.com/v1/currencies?apikey=" + API_KEY);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONObject data = jsonResponse.getJSONObject("data");

                for (String key : data.keySet()) {
                    JSONObject currencyData = data.getJSONObject(key);
                    String symbol = currencyData.getString("code");
                    symbols.add(symbol);
                }
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return symbols;
    }


    @Override
    public ExchangeRate loadCurrencyExchangeRate(String baseCurrency, String targetCurrency) {
        ExchangeRate exchangeRate = null;
        try {
            URL url = new URL("https://api.freecurrencyapi.com/v1/latest?apikey=" + API_KEY + "&base_currency=" + baseCurrency + "&currencies=" + targetCurrency);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                JSONObject data = jsonResponse.getJSONObject("data");

                double rate = data.getDouble(targetCurrency);
                exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
            } else {
                throw new InvalidCurrencyException("Invalid currency");
            }
            connection.disconnect();

        } catch (InvalidCurrencyException e) {
            throw new InvalidCurrencyException("Invalid currency");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }
}
