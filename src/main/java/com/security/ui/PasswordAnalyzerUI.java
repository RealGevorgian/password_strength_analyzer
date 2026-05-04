package com.security.ui;

import com.security.analyzer.AnalysisResult;
import com.security.analyzer.PasswordAnalyzer;
import com.security.features.PassphraseGenerator;
import com.security.features.PasswordReuseChecker;
import com.security.features.CrackTimeEstimator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PasswordAnalyzerUI extends JFrame{

    private JTextField passwordField;
    private JTextArea resultArea;
    private JCheckBox reuseCheckBox;
    private JComboBox<String> attackModeBox;
    private JProgressBar strengthBar;

    private PasswordAnalyzer analyzer;

    private final Color BG_COLOR = new Color(30, 30, 30);
    private final Color PANEL_COLOR = new Color(45, 45, 45);
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    public PasswordAnalyzerUI(){

        analyzer = new PasswordAnalyzer();

        setTitle("Password Strength Analyzer");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        JPanel topPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        topPanel.setBackground(PANEL_COLOR);

        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        passwordField = new JTextField();
        passwordField.setBackground(new Color(60, 60, 60));
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setCaretColor(TEXT_COLOR);

        reuseCheckBox = new JCheckBox("I reuse this password on multiple accounts");
        reuseCheckBox.setBackground(PANEL_COLOR);
        reuseCheckBox.setForeground(TEXT_COLOR);

        attackModeBox = new JComboBox<>(new String[]{
                "Normal Attacker",
                "GPU Attacker"
        });
        attackModeBox.setBackground(PANEL_COLOR);
        attackModeBox.setForeground(TEXT_COLOR);

        strengthBar = new JProgressBar(0, 100);
        strengthBar.setStringPainted(true);
        strengthBar.setBackground(new Color(60, 60, 60));

        topPanel.add(label);
        topPanel.add(passwordField);
        topPanel.add(reuseCheckBox);
        topPanel.add(attackModeBox);
        topPanel.add(strengthBar);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setBackground(new Color(20, 20, 20));
        resultArea.setForeground(TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.getViewport().setBackground(new Color(20, 20, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(PANEL_COLOR);

        JButton analyzeButton = new JButton("Analyze");
        JButton generateButton = new JButton("Generate Passphrase");

        styleButton(analyzeButton);
        styleButton(generateButton);

        bottomPanel.add(analyzeButton);
        bottomPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        analyzeButton.addActionListener(e -> analyzePassword());
        generateButton.addActionListener(e -> generatePassphrase());

        passwordField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){ analyzePassword();}
            public void removeUpdate(DocumentEvent e){ analyzePassword();}
            public void changedUpdate(DocumentEvent e){ analyzePassword();}
        });
    }

    private void styleButton(JButton button){
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
    }

    private void analyzePassword(){

        String password = passwordField.getText();

        if(password.isEmpty()){
            resultArea.setText("Please enter a password.");
            strengthBar.setValue(0);
            return;
        }

        AnalysisResult result = analyzer.analyze(password);

        strengthBar.setValue(result.getScore());
        strengthBar.setString(result.getScore() + "/100");

        if(result.getStrength().toString().equals("WEAK")){
            resultArea.setForeground(Color.RED);
            strengthBar.setForeground(Color.RED);
        } else if(result.getStrength().toString().equals("MEDIUM")){
            resultArea.setForeground(Color.ORANGE);
            strengthBar.setForeground(Color.ORANGE);
        } else{
            resultArea.setForeground(new Color(0, 200, 0));
            strengthBar.setForeground(new Color(0, 200, 0));
        }

        boolean isReused = reuseCheckBox.isSelected();
        String reuseMessage = PasswordReuseChecker.check(isReused);

        String selected = (String) attackModeBox.getSelectedItem();

        CrackTimeEstimator.AttackMode mode =
                selected.equals("GPU Attacker")
                        ? CrackTimeEstimator.AttackMode.GPU
                        : CrackTimeEstimator.AttackMode.NORMAL;

        String crackTime = CrackTimeEstimator.estimate(password, mode);

        resultArea.setText(
                "Strength: " + result.getStrength() + "\n" +
                        "Score: " + result.getScore() + "/100\n" +
                        "Entropy: " + String.format("%.2f", result.getEntropy()) + " bits\n" +
                        "Crack Time: " + crackTime + "\n" +
                        "Attack Type: " + result.getAttackType() + "\n" +
                        "Feedback: " + result.getFeedback() + "\n\n" +
                        "Reuse Risk: " + reuseMessage
        );
    }

    private void generatePassphrase(){
        String passphrase = PassphraseGenerator.generate();
        passwordField.setText(passphrase);
    }
}