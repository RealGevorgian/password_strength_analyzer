package com.security.features;

public class CrackTimeEstimator{

    public enum AttackMode {
        NORMAL,   // ~1e9 guesses/sec
        GPU       // ~1e12 guesses/sec
    }

    public static double calculateEntropy(String password){
        int charsetSize = calculateCharsetSize(password);
        int length = password.length();

        if(charsetSize == 0 || length == 0) return 0;

        return length * (Math.log(charsetSize) / Math.log(2));
    }

    public static String estimate(String password, AttackMode mode){

        double guessesPerSecond = (mode == AttackMode.GPU)
                ? 1_000_000_000_000.0
                : 1_000_000_000.0;

        double entropy = calculateEntropy(password);
        double combinations = Math.pow(2, entropy);
        double seconds = combinations / guessesPerSecond;

        return formatTime(seconds);
    }

    private static int calculateCharsetSize(String password){
        int size = 0;

        if(password.matches(".*[a-z].*")) size += 26;
        if(password.matches(".*[A-Z].*")) size += 26;
        if(password.matches(".*\\d.*")) size += 10;
        if(password.matches(".*[!@#$%^&*()].*")) size += 10;

        return size;
    }

    private static String formatTime(double seconds){
        if(seconds < 1) return "Less than a second";
        if(seconds < 60) return (int) seconds + " seconds";
        if(seconds < 3600) return (int)(seconds / 60) + " minutes";
        if(seconds < 86400) return (int)(seconds / 3600) + " hours";
        if(seconds < 31536000) return (int)(seconds / 86400) + " days";

        return (int)(seconds / 31536000) + " years";
    }
}