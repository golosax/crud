package com.ce.crud.fixerio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixerError {

    @JsonProperty("code")
    private int errorCode;

    @JsonProperty("type")
    private String errorType;

    @JsonProperty("info")
    private String errorInfo;

    @Override
    public String toString() {
        return "error code: " + getErrorCode() + ", type: " + errorType + ", info: " + getErrorInfo();
    }
}
