package com.security.analyzer;

import com.security.features.AttackTypeDetector;
import com.security.features.CrackTimeEstimator;
import com.security.utils.CommonPasswords;

public class PasswordAnalyzer{

    public AnalysisResult analyze(String password){

        int score = 0;
        StringBuilder feedback = new StringBuilder();

        if(password.length() >= 8) score += 25;
        else feedback.append("Password is too short. ");

        if(password.matches(".*[A-Z].*")) score += 15;
        else feedback.append("Add uppercase letters. ");

        if(password.matches(".*[a-z].*")) score += 15;
        else feedback.append("Add lowercase letters. ");

        if(password.matches(".*\\d.*")) score += 15;
        else feedback.append("Add numbers. ");

        if(password.matches(".*[!@#$%^&*()].*")) score += 20;
        else feedback.append("Add special characters. ");

        if(CommonPasswords.isCommon(password)){
            score = 0;
            feedback = new StringBuilder("This is a very common password.");
        }

        StrengthLevel level;
        if(score < 40) level = StrengthLevel.WEAK;
        else if(score < 70) level = StrengthLevel.MEDIUM;
        else level = StrengthLevel.STRONG;

        double entropy = CrackTimeEstimator.calculateEntropy(password);

        String crackTime = CrackTimeEstimator.estimate(
                password,
                CrackTimeEstimator.AttackMode.NORMAL
        );

        String attackType = AttackTypeDetector.detect(password);

        return new AnalysisResult(
                level,
                score,
                crackTime,
                attackType,
                feedback.toString(),
                entropy
        );
    }
}