package com.example.springsecurityusersroles.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Status {

    ACTIVE,
    BANNED;

    public static List<String> getValuesAsString () {
        return Arrays.stream(values()).map(Object::toString).collect(Collectors.toList());
    }
}
