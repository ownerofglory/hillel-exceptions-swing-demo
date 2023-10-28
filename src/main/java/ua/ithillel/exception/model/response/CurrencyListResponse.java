package ua.ithillel.exception.model.response;

import java.util.Map;

public class CurrencyListResponse {
    private Map<String, String> currencies;

    public CurrencyListResponse(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    public Map<String, String> getCurrencies() {
        return currencies;
    }
}
