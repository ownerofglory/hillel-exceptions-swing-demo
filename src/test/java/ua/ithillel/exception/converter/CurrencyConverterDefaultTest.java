package ua.ithillel.exception.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.ithillel.exception.client.CurrencyApiClient;
import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.exception.ConversionException;
import ua.ithillel.exception.model.Currency;
import ua.ithillel.exception.model.response.CurrencyListResponse;
import ua.ithillel.exception.model.response.CurrencyRateResponse;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CurrencyConverterDefaultTest {
    @Mock
    private CurrencyApiClient currencyApiClient;

    private CurrencyConverter currencyConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        currencyConverter = new CurrencyConverterDefault(currencyApiClient);
    }

    @Test
    public void getAllCurrenciesTest_returnsListOfCurrencies() throws ClientException {
        final CurrencyListResponse mockResponse = new CurrencyListResponse(Map.of("test", "test"));
        when(currencyApiClient.getList()).thenReturn(mockResponse);

        final List<Currency> allCurrencies = currencyConverter.getAllCurrencies();

        assertNotNull(allCurrencies);
        assertFalse(allCurrencies.isEmpty());
    }

    @Test
    public void getAllCurrenciesTest_throwsException() throws ClientException {
        when(currencyApiClient.getList()).thenThrow(ClientException.class);

        assertThrows(ClientException.class, () -> {
            final List<Currency> allCurrencies = currencyConverter.getAllCurrencies();
        });
    }

    @Test
    public void convertTest_returnsValue() throws ClientException, ConversionException {
        final CurrencyRateResponse mockResponse = new CurrencyRateResponse(Map.of("c2", "1.0"));
        when(currencyApiClient.getRate(anyString(), anyString())).thenReturn(mockResponse);

        double testValue = 100.0;
        final double converted = currencyConverter.convert(testValue,
                new Currency("c1", "c1"),
                new Currency("c2", "c2"));

        assertTrue(converted > 0);
    }

    @Test
    public void convertTest_throwConversionException()  {
        double testValue = -100;

        assertThrows(ConversionException.class, () ->
                currencyConverter.convert(testValue, new Currency("c1", "c1"), new Currency("c2", "c2"))
        );

    }

    @Test
    public void convertTest_throwClientException() throws ClientException {
        when(currencyApiClient.getRate(anyString(), anyString())).thenThrow(ClientException.class);

        double testValue = 100;

        assertThrows(ClientException.class, () ->
                currencyConverter.convert(testValue, new Currency("c1", "c1"), new Currency("c2", "c2"))
        );

    }

}
