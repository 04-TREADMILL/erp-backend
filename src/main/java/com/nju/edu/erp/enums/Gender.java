package com.nju.edu.erp.enums;

public enum Gender implements BaseEnum<Gender, String> {
    MALE("男"),
    FEMALE("女");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
