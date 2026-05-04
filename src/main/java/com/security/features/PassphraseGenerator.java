package com.security.features;

import java.util.Random;

public class PassphraseGenerator {

    private static final String[] WORDS = {
            "Blue", "Tiger", "Mountain", "River", "Sunset", "Coffee", "Forest", "Sky"
    };

    public static String generate() {
        Random rand = new Random();
        return WORDS[rand.nextInt(WORDS.length)] +
                WORDS[rand.nextInt(WORDS.length)] +
                "!" + rand.nextInt(100);
    }
}