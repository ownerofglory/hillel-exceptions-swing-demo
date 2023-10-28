package ua.ithillel.exception.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.ithillel.exception.exception.ClientException;
import ua.ithillel.exception.model.response.CurrencyListResponse;
import ua.ithillel.exception.model.response.CurrencyRateResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class CurrencyApiClientDefault implements CurrencyApiClient {
    private static final String BASE_URL
            = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CurrencyApiClientDefault(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public CurrencyListResponse getList() throws ClientException {
        try {
            String url = BASE_URL + "/currencies.json";

            HttpRequest request = buildRequest(url);

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                //
                throw new ClientException("Server returned unsuccessful code");
            }

            Map<String, String> currencyMap = objectMapper.readValue(response.body(), new TypeReference<Map<String, String>>() {
            });

            return new CurrencyListResponse(currencyMap);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
           throw new ClientException("Unable to call remote server");
        }
    }

    @Override
    public CurrencyRateResponse getRate(String source, String target) throws ClientException {
        try {
            String url = BASE_URL + "/currencies/%s/%s.json".formatted(source, target);


            HttpRequest request = buildRequest(url);

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                //
                throw new ClientException("Server returned unsuccessful code");
            }

            Map<String, String> rateMap = objectMapper.readValue(response.body(), new TypeReference<Map<String, String>>() {
            });

            return new CurrencyRateResponse(rateMap);

        }  catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new ClientException("Unable to call remote server");
        }

    }

    private HttpRequest buildRequest(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
    }
}
