package ua.ithillel.exception.converter;

import ua.ithillel.exception.client.CurrencyApiClient;
import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.exception.ConversionException;
import ua.ithillel.exception.model.Currency;
import ua.ithillel.exception.model.response.CurrencyListResponse;
import ua.ithillel.exception.model.response.CurrencyRateResponse;
import ua.ithillel.exception.util.StringUtil;

import java.util.List;
import java.util.Map;

public class CurrencyConverterDefault implements CurrencyConverter {
    private final CurrencyApiClient client;

    public CurrencyConverterDefault(CurrencyApiClient client) {
        this.client = client;
    }

    @Override
    public List<Currency> getAllCurrencies() throws ClientException {
        CurrencyListResponse list = client.getList();
        Map<String, String> currencies = list.getCurrencies();

        return currencies.keySet()
                .stream()
                .map(key -> new Currency(key,currencies.get(key)))
                .toList();

    }

    @Override
    public double convert(double value, Currency sourceCurrency, Currency targetCurrency) throws ConversionException, ClientException {
        if (value < 0) {
            throw new ConversionException("Source value can be only positive");
        }

        CurrencyRateResponse response = client.getRate(sourceCurrency.getCode(), targetCurrency.getCode());
        Map<String, String> rates = response.getRates();
        String rateStr = rates.get(targetCurrency.getCode());

        double rateValue = StringUtil.convertDouble(rateStr);

        return value * rateValue;
    }
}
