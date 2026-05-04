package com.security.features;

public class AttackTypeDetector{

    public static String detect(String password){

        if(password.matches("^[a-zA-Z]+$")){
            return "Dictionary Attack";
        }

        if(password.matches("^[0-9]+$")){
            return "Brute-force Attack";
        }

        return "Hybrid Attack";
    }
}