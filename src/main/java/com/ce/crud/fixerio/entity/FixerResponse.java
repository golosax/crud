package com.ce.crud.fixerio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixerResponse {

    @JsonProperty("success")
    Boolean success;

    @JsonProperty("timestamp")
    Long timestamp;

    @JsonProperty("base")
    String baseCurrency;

    @JsonProperty("date")
    Date date;

    @JsonProperty("rates")
    Map<String, Double> rates;

    @JsonProperty("error")
    FixerError error;
}
