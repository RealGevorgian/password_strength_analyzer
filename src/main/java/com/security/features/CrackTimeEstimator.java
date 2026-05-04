package com.security.features;

public class CrackTimeEstimator{

    public enum AttackMode{
        NORMAL,
        GPU
    }

    public static double calculateEntropy(String password){
        int charsetSize = calculateCharsetSize(password);
        int length = password.length();

        if(charsetSize == 0 || length == 0){
            return 0;
        }

        return length * (Math.log(charsetSize) / Math.log(2));
    }

    public static String estimate(String password, AttackMode mode){
        double entropy = calculateEntropy(password);

        // GPU attackers reduce effective security threshold slightly
        if(mode == AttackMode.GPU){
            entropy -= 8;
        }

        return mapEntropyToApproximateTime(entropy);
    }

    private static int calculateCharsetSize(String password){
        int size = 0;

        if (password.matches(".*[a-z].*")) size += 26;
        if (password.matches(".*[A-Z].*")) size += 26;
        if (password.matches(".*\\d.*")) size += 10;
        if (password.matches(".*[!@#$%^&*()].*")) size += 10;

        return size;
    }

    private static String mapEntropyToApproximateTime(double entropy){

        if(entropy < 20){
            return "Less than a second";
        }else if (entropy < 30) {
            return "Several seconds";
        }else if (entropy < 40) {
            return "Several minutes";
        }else if (entropy < 50) {
            return "Several hours";
        }else if (entropy < 60) {
            return "Several days";
        }else if (entropy < 70) {
            return "Several weeks";
        }else {
            return "Up to 1 year";
        }
    }
}