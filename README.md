# Password Security Analyzer

A Java-based cybersecurity application that evaluates password strength and provides real-time security feedback through an interactive graphical user interface.

## Overview

This project was developed as part of a cybersecurity course to help users understand password security best practices and assess the strength of their passwords using both rule-based analysis and entropy-based metrics.

The application analyzes passwords in real time and estimates crack time under different attacker models while providing actionable security recommendations.

## Features

* Real-time password strength analysis while typing
* Entropy-based password unpredictability calculation
* Password crack time estimation using multiple attacker models
* Support for Normal and GPU-based attacker simulations
* Password reuse risk detection
* Passphrase generator for stronger password suggestions
* Visual password strength progress bar
* Dark-themed graphical user interface
* Attack type detection (e.g., dictionary/brute-force/hybrid)

## Technologies Used

* Java
* Java Swing (GUI)
* Maven

## Project Structure

```text
PasswordAnalyzerProject/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/security/
│   │   │       ├── Main.java
│   │   │       ├── analyzer/
│   │   │       │   ├── AnalysisResult.java
│   │   │       │   ├── PasswordAnalyzer.java
│   │   │       │   └── StrengthLevel.java
│   │   │       ├── features/
│   │   │       │   ├── AttackTypeDetector.java
│   │   │       │   ├── CrackTimeEstimator.java
│   │   │       │   ├── PassphraseGenerator.java
│   │   │       │   └── PasswordReuseChecker.java
│   │   │       ├── ui/
│   │   │       │   └── PasswordAnalyzerUI.java
│   │   │       └── utils/
│   │   │           └── CommonPasswords.java
│   │   └── resources/
│   └── test/
├── pom.xml
├── .gitignore
└── README.md
```

## How It Works

1. The user enters a password into the application.
2. The system evaluates password complexity using rule-based checks:

    * Length
    * Uppercase/lowercase letters
    * Numbers
    * Special characters
3. Entropy is calculated to estimate password unpredictability.
4. Crack time is estimated based on selected attacker strength.
5. Password reuse risk is optionally simulated.
6. The application provides feedback and improvement suggestions.

## Running the Project

### Prerequisites

* Java 17+ recommended
* Maven installed
* IDE such as IntelliJ IDEA or VS Code

### Run with Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.security.Main"
```

### Run from IDE

Open the project in your IDE and run `Main.java`.

## Future Improvements

* Enhanced entropy model for more realistic crack-time estimates
* Integration with password breach databases
* Exportable password security reports
* Improved UI/UX design enhancements

## Academic Context

Developed for a university cybersecurity project focused on password security analysis and user awareness.
