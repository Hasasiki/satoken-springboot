package com.ha.satoken.data.eunm;

import java.util.Arrays;

public enum ProType {
    //0: admin
    //1: user
    ADMIN(0, "admin"),
    USER(1, "user"),
    UNKNOWN(-1, "unknown");

    private final int value;
    private final String name;

    ProType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    public static ProType getEnum(int value) {
        return Arrays.stream(values()).filter(s -> s.getValue() == value).findFirst().orElse(UNKNOWN);
    }
}
