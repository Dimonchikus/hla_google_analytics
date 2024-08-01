package com.googleanalytics;

import org.json.JSONArray;
import org.springframework.web.reactive.function.client.WebClient;

public class ExchangeRateFetcher {

    private static final String EXCHANGE_RATE_API = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&json";

    public static Double getUsdToUahRate() {
        return WebClient.create(EXCHANGE_RATE_API)
            .get()
            .retrieve()
            .bodyToMono(String.class)
            .map(response -> parseExchangeRate(response)).block();
    }

    private static Double parseExchangeRate(String jsonResponse) {
        var jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            var jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("cc").equals("USD")) {
                return jsonObject.getDouble("rate");
            }
        }
        return null;
    }
}