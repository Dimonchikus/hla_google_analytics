package com.googleanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GoogleAnalyticsApp {

    public static void main(String[] args) {
        SpringApplication.run(GoogleAnalyticsApp.class);
    }
}
