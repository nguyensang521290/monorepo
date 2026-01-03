package com.gnas.starter.accountservice.domain;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ACTIVE("ACTIVE"),
    FROZEN("FROZEN"),
    CLOSED("CLOSED"),
    ;

    private final String value;

    AccountStatus (String val) {
        this.value = val;
    }
}