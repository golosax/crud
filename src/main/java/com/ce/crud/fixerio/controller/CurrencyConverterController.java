package com.ce.crud.fixerio.controller;

import com.ce.crud.fixerio.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;

    @GetMapping("convert/{amount}/{fromCurrency}")
    public String convert(@PathVariable BigDecimal amount,
                          @PathVariable String fromCurrency) {
        final String toCurrency = "EUR"; // EUR is default currency for "/api/latest" in fixer.io
        log.info(String.format("Request to convert : %s %s to %s", amount, fromCurrency, toCurrency));
        return currencyConverterService.convert(amount, fromCurrency, toCurrency);
    }

}
