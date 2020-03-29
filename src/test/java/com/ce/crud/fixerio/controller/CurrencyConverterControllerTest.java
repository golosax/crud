package com.ce.crud.fixerio.controller;

import com.ce.crud.fixerio.service.CurrencyConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyConverterController.class)
@ExtendWith(SpringExtension.class)
class CurrencyConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConverterService currencyConverterService;


    @Test
    void convert() throws Exception {

        final String toCurrency = "EUR";
        final String fromCurrency = "USD";
        final BigDecimal amount = BigDecimal.TEN;

        // when
        when(currencyConverterService.convert(amount, fromCurrency, toCurrency)).thenReturn("11");

        // test
        mockMvc.perform(get("/convert/{amount}/{fromCurrency}", amount, fromCurrency))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("11"));

        verify(currencyConverterService, times(1)).convert(amount, fromCurrency, toCurrency);
    }

}