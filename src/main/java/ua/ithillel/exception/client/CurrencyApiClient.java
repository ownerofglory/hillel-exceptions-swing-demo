package ua.ithillel.exception.client;

import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.model.response.CurrencyListResponse;
import ua.ithillel.exception.model.response.CurrencyRateResponse;

public interface CurrencyApiClient {
    CurrencyListResponse getList() throws ClientException;
    CurrencyRateResponse getRate(String source, String target) throws ClientException;
}
