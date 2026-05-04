package com.security.analyzer;

public class AnalysisResult {

    private StrengthLevel strength;
    private int score;
    private String crackTime;
    private String attackType;
    private String feedback;
    private double entropy;

    public AnalysisResult(StrengthLevel strength, int score, String crackTime,
                          String attackType, String feedback, double entropy){
        this.strength = strength;
        this.score = score;
        this.crackTime = crackTime;
        this.attackType = attackType;
        this.feedback = feedback;
        this.entropy = entropy;
    }

    public StrengthLevel getStrength(){
        return strength;
    }

    public int getScore(){
        return score;
    }

    public String getCrackTime(){
        return crackTime;
    }

    public String getAttackType(){
        return attackType;
    }

    public String getFeedback(){
        return feedback;
    }

    public double getEntropy(){
        return entropy;
    }
}