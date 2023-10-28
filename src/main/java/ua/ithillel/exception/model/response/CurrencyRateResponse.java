package ua.ithillel.exception.model.response;

import java.util.Map;

public class CurrencyRateResponse {
    private Map<String, String> rates;

    public CurrencyRateResponse(Map<String, String> rates) {
        this.rates = rates;
    }

    public Map<String, String> getRates() {
        return rates;
    }
}
