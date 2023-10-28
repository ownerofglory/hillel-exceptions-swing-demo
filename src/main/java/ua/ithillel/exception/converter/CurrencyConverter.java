package ua.ithillel.exception.converter;

import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.exception.ConversionException;
import ua.ithillel.exception.model.Currency;

import java.util.List;

public interface CurrencyConverter {
    List<Currency> getAllCurrencies() throws ClientException;
    double convert(double value, Currency sourceCurrency, Currency targetCurrency) throws ConversionException, ClientException;
}
