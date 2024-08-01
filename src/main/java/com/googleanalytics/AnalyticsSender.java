package com.googleanalytics;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AnalyticsSender {

    private static final String MEASUREMENT_ID = "G-72XTKQPCRK";
    private static final String API_SECRET = "0Vb8NM2gR7GOsyGHUPXfjw"; //
    private static final String CLIENT_ID = "8523746401";
    private static final String BASE_URL = "https://www.google-analytics.com";

    private final WebClient webClient;

    public AnalyticsSender() {
        this.webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();
    }

    public void sendExchangeRateEvent(double exchangeRate) {
        String requestBody = "{"
            + "\"client_id\": \"" + CLIENT_ID + "\","
            + "\"events\": [{"
            + "\"name\": \"add_payment_info\","
            + "\"params\": {"
            + "\"from_currency\": \"USD\","
            + "\"to_currency\": \"UAH\","
            + "\"exchange_rate\": " + exchangeRate
            + "}}]}";

        Mono<Void> response = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/mp/collect")
                .queryParam("measurement_id", MEASUREMENT_ID)
                .queryParam("api_secret", API_SECRET)
                .build())
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(requestBody))
            .retrieve()
            .bodyToMono(Void.class)
            .doOnSuccess(v -> System.out.println("Event sent successfully"))
            .doOnError(e -> System.out.println("Error sending event: " + e.getMessage()));

        response.block();
    }
}