package com.security.utils;

import java.util.Arrays;
import java.util.List;

public class CommonPasswords{

    private static final List<String> COMMON = Arrays.asList(
            "123456",
            "password",
            "qwerty",
            "admin"
    );

    public static boolean isCommon(String password){
        return COMMON.contains(password.toLowerCase());
    }
}