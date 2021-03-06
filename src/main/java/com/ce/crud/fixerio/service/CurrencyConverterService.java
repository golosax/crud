package com.ce.crud.fixerio.service;

import com.ce.crud.fixerio.entity.Fixer;
import com.ce.crud.fixerio.entity.FixerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConverterService {

    @Value("${service.fixerio.url}")
    private String fixerioUrl;

    @Value("${service.fixerio.access_key}")
    private String accessKey;

    private static final String LATEST = "/latest";
    private static final String UNKNOWN_RATE = "n/a";
    private static final String FIXER_ERROR = "Error during fixer.io api call - %s";

    private final RestTemplate restTemplate;

    public String convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (toCurrency.equals(fromCurrency)) {
            return amount.toString();
        }
        Map<String, Double> rates = getRates(toCurrency);
        if (!rates.containsKey(fromCurrency)) {
            log.warn(String.format("Can not convert currency from %s to %s.", fromCurrency, toCurrency));
            return UNKNOWN_RATE;
        }
        BigDecimal rate = BigDecimal.ONE.divide(BigDecimal.valueOf(rates.get(fromCurrency)), MathContext.DECIMAL32); //count approximate rate
        BigDecimal result = amount.multiply(rate);
        return result.toString();
    }

    Map<String, Double> getRates(String baseCurrency) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(fixerioUrl + LATEST)
                .queryParam(Fixer.ACCESS_KEY.getValue(), accessKey);

        log.info(String.format("Fixer API call for currency [%s].", baseCurrency));
        FixerResponse payload = restTemplate.getForObject(uriBuilder.toUriString(), FixerResponse.class);

        if (payload != null && payload.getSuccess()) {
            return payload.getRates();
        } else {
            log.error(String.format(FIXER_ERROR, payload == null ? "payload is null" : payload.getError()));
        }

        return Collections.emptyMap();
    }
}

