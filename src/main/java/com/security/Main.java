package com.security;

import com.security.ui.PasswordAnalyzerUI;

public class Main{
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(()->{
            new PasswordAnalyzerUI().setVisible(true);
        });
    }
}