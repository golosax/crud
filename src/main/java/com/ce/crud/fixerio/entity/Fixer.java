package com.ce.crud.fixerio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Fixer {

    ACCESS_KEY("access_key"),
    FROM_CURRENCY("from"),
    TO_CURRENCY("to"),
    AMOUNT("amount"),
    BASE("base");

    private String value;

}
