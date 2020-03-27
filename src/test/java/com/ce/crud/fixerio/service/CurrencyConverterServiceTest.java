package com.ce.crud.fixerio.service;


import com.ce.crud.fixerio.entity.FixerError;
import com.ce.crud.fixerio.entity.FixerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CurrencyConverterServiceTest {

    @Mock
    private CurrencyConverterService currencyConverterServiceMock;

    private CurrencyConverterService currencyConverterService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        currencyConverterService = new CurrencyConverterService(restTemplate);
    }

    @Test
    public void convert_successCaseWithFractionalAmount() throws Exception {
        // given
        Map<String, Double> rates = new HashMap() {{
            put("USD", 1.1);
            put("BTC", 0.00014);
        }};

        // when
        doReturn(rates).when(currencyConverterServiceMock).getRates("EUR");
        when(currencyConverterServiceMock.convert(any(), any(), any())).thenCallRealMethod();

        // test
        assertEquals("6409.74451466225", currencyConverterServiceMock.convert(BigDecimal.valueOf(0.89736425), "BTC", "EUR"));
    }

    @Test
    public void convert_emptyRates() {
        // when
        doReturn(Collections.emptyMap()).when(currencyConverterServiceMock).getRates(any(String.class));
        when(currencyConverterServiceMock.convert(any(), any(), any())).thenCallRealMethod();

        // test
        assertEquals(currencyConverterServiceMock.convert(BigDecimal.valueOf(1), "BTC", "EUR"), "n/a");
    }

    @Test
    public void getRates_successResponseWithRates() throws Exception {
        // given
        Map<String, Double> rates = new HashMap() {{
            put("USD", 1.1);
            put("PLN", 4.25);
        }};
        FixerResponse response = FixerResponse.builder()
                .rates(rates)
                .success(true)
                .build();

        // when
        doReturn(response).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));
        currencyConverterService = new CurrencyConverterService(restTemplate);

        // test
        assertEquals(currencyConverterService.getRates("EUR"), rates);
    }

    @Test
    public void getRates_successResponseWithEmptyRates() throws Exception {
        // given
        FixerResponse response = FixerResponse.builder()
                .rates(Collections.emptyMap())
                .success(true)
                .build();

        // when
        doReturn(response).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));

        // test
        assertEquals(currencyConverterService.getRates("EUR"), Collections.emptyMap());
    }

    @Test
    public void getRates_error() {
        // given
        FixerError fe = FixerError.builder()
                .errorCode(123)
                .errorInfo("something went wrong")
                .errorType("wrong request")
                .build();

        FixerResponse responseWithError = FixerResponse.builder()
                .error(fe)
                .success(false)
                .build();

        // when
        doReturn(responseWithError).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));

        // test
        assertEquals(currencyConverterService.getRates("EUR"), Collections.emptyMap());
    }

    @Test
    public void getRates_shouldReturnEmptyMapIfPayloadIsNull() throws Exception {
        // when
        doReturn(null).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));

        // test
        assertEquals(currencyConverterService.getRates("EUR"), Collections.emptyMap());
    }


}
