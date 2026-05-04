package com.security.features;

public class PasswordReuseChecker{

    public static String check(boolean reused){
        if(reused){
            return "High risk: password reuse can compromise multiple accounts.";
        }
        return "No reuse detected.";
    }
}