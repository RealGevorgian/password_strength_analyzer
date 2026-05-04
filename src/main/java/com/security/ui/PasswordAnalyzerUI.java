package com.security.ui;

import com.security.analyzer.AnalysisResult;
import com.security.analyzer.PasswordAnalyzer;
import com.security.features.CrackTimeEstimator;
import com.security.features.PassphraseGenerator;
import com.security.features.PasswordReuseChecker;

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
    private JLabel statusLabel;

    private PasswordAnalyzer analyzer;

    private final Color BG_COLOR = new Color(30, 30, 30);
    private final Color PANEL_COLOR = new Color(45, 45, 45);
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    public PasswordAnalyzerUI(){

        analyzer = new PasswordAnalyzer();

        setTitle("Password Strength Analyzer");
        setSize(700, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(PANEL_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JTextField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBackground(new Color(60, 60, 60));
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setCaretColor(TEXT_COLOR);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        reuseCheckBox = new JCheckBox("I reuse this password on multiple accounts");
        reuseCheckBox.setBackground(PANEL_COLOR);
        reuseCheckBox.setForeground(TEXT_COLOR);
        reuseCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        attackModeBox = new JComboBox<>(new String[]{
                "Normal Attacker",
                "GPU Attacker"
        });
        attackModeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        attackModeBox.setBackground(PANEL_COLOR);
        attackModeBox.setForeground(TEXT_COLOR);

        strengthBar = new JProgressBar(0, 100);
        strengthBar.setStringPainted(true);
        strengthBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        strengthBar.setBackground(new Color(60, 60, 60));

        statusLabel = new JLabel("SECURITY STATUS: -");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(label);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(passwordField);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(reuseCheckBox);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(attackModeBox);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(strengthBar);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(statusLabel);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultArea.setBackground(new Color(20, 20, 20));
        resultArea.setForeground(TEXT_COLOR);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(PANEL_COLOR, 2));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton analyzeButton = new JButton("Analyze");
        JButton generateButton = new JButton("Generate Passphrase");

        styleButton(analyzeButton);
        styleButton(generateButton);

        bottomPanel.add(analyzeButton);
        bottomPanel.add(generateButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

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
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.BOLD, 13));
    }

    private void animateProgressBar(int targetValue){
        Timer timer = new Timer(10, null);

        timer.addActionListener(e ->{
            int current = strengthBar.getValue();

            if (current < targetValue) {
                strengthBar.setValue(current + 1);
            } else if (current > targetValue) {
                strengthBar.setValue(current - 1);
            } else {
                timer.stop();
            }
        });

        timer.start();
    }

    private void analyzePassword(){

        String password = passwordField.getText();

        if(password.isEmpty()){
            resultArea.setText("Please enter a password.");
            strengthBar.setValue(0);
            statusLabel.setText("SECURITY STATUS: -");
            passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            return;
        }

        AnalysisResult result = analyzer.analyze(password);

        animateProgressBar(result.getScore());
        strengthBar.setString(result.getScore() + "/100");

        if(result.getStrength().toString().equals("WEAK")){
            applyThemeColor(Color.RED);
        } else if(result.getStrength().toString().equals("MEDIUM")){
            applyThemeColor(Color.ORANGE);
        } else{
            applyThemeColor(new Color(0, 200, 0));
        }

        statusLabel.setText("SECURITY STATUS: " + result.getStrength());

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
                        "Approximate Crack Time: " + crackTime + "\n" +
                        "Attack Type: " + result.getAttackType() + "\n" +
                        "Feedback: " + result.getFeedback() + "\n\n" +
                        "Reuse Risk: " + reuseMessage
        );
    }

    private void applyThemeColor(Color color){
        resultArea.setForeground(color);
        strengthBar.setForeground(color);
        statusLabel.setForeground(color);
        passwordField.setBorder(BorderFactory.createLineBorder(color, 2));
    }

    private void generatePassphrase(){
        String passphrase = PassphraseGenerator.generate();
        passwordField.setText(passphrase);
    }
}