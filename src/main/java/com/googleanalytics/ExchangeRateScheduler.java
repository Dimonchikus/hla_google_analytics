package com.googleanalytics;

import static java.util.concurrent.TimeUnit.HOURS;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateScheduler {

    private final AnalyticsSender analyticsSender;

    public ExchangeRateScheduler(AnalyticsSender analyticsSender) {
        this.analyticsSender = analyticsSender;
    }

    @Scheduled(timeUnit = HOURS, fixedDelayString = "1")
    public void rateExchangeTask() {
        var rate = ExchangeRateFetcher.getUsdToUahRate();
        System.out.println("Exchange rate rate: " + rate);
        analyticsSender.sendExchangeRateEvent(rate);
    }
}