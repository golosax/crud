package com.ce.crud.fixerio.service;


import com.ce.crud.fixerio.entity.FixerError;
import com.ce.crud.fixerio.entity.FixerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class CurrencyConverterServiceTest {

    @MockBean
    CurrencyConverterService ccsMock;

    @MockBean
    RestTemplate restTemplate;

    private CurrencyConverterService ccs = new CurrencyConverterService(restTemplate);


    @Test
    public void convert() throws Exception {
        Map<String, Double> rates = new HashMap() {{
            put("USD", 1.1);
            put("EUR", 9168.25);
        }};

        // when
        doReturn(rates).when(ccsMock).getRates(any(String.class));
//        when(ccsMock.convert(any(), any(), any())).thenCallRealMethod();

        // test
//        assertEquals(ccsMock.convert(BigDecimal.valueOf(0.89736425),"BTC", "EUR"), "8227.2597850625");
    }

    @Test
    public void convert_emptyRates() {
        // when
        doReturn(Collections.emptyMap()).when(ccsMock).getRates(any(String.class));
//        when(ccsMock.convert(any(), any(), any())).thenCallRealMethod();

        // test
//        assertEquals(ccsMock.convert( BigDecimal.valueOf(1),"BTC", "EUR"), "n/a");
    }

    @Test
    public void getRates_successResponseWithRates() throws Exception {
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
        ccs = new CurrencyConverterService(restTemplate);

        // test
        assertEquals(ccs.getRates("EUR"), rates);
    }

    @Test
    public void getRates_successResponseWithEmptyRates() throws Exception {
        FixerResponse response = FixerResponse.builder()
                .rates(Collections.emptyMap())
                .success(true)
                .build();

        // when
        doReturn(response).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));
        ccs = new CurrencyConverterService(restTemplate);

        // test
        assertEquals(ccs.getRates("EUR"), Collections.emptyMap());
    }

    @Test
    public void getRates_error() throws Exception {
        FixerError fe = FixerError.builder()
                .errorCode(123)
                .errorInfo("something went wrong")
                .errorType("wrong request")
                .build();

        FixerResponse response = FixerResponse.builder()
                .error(fe)
                .success(false)
                .build();

        // when
        doReturn(response).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));
        ccs = new CurrencyConverterService(restTemplate);

        // test
        assertEquals(ccs.getRates("EUR"), Collections.emptyMap());
    }

    @Test
    public void getRates_shouldReturnEmptyMapIfPayloadIsNull() throws Exception {

        // when
        doReturn(null).when(restTemplate).getForObject(Mockito.any(String.class), eq(FixerResponse.class));
        ccs = new CurrencyConverterService(restTemplate);

        // test
        assertEquals(ccs.getRates("EUR"), Collections.emptyMap());
    }


}
