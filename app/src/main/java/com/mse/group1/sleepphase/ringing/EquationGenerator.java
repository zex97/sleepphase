package com.mse.group1.sleepphase.ringing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

public class EquationGenerator {

    private static Random random = new Random();

    private static final String operations = "+-*";
    private static final String operationsHard = "*/";
    private static final String operationsPlusMinus = "+-";

    public static String generateEquation(int difficulty) {
        if (difficulty == 0) {
            return generateEasy();
        } else if (difficulty == 1) {
            return generateMedium();
        }
        return generateHard();
    }

    private static String generateEasy() {
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        return a + "" + operations.charAt(random.nextInt(operations.length())) + "" + b;
    }

    private static String generateMedium() {
        int a = random.nextInt(10) + 6;
        int b = random.nextInt(10) + 6;
        int c = random.nextInt(10) + 6;
        return a + "" + operations.charAt(random.nextInt(operations.length())) + "" + b + operations.charAt(random.nextInt(operations.length())) + "" + c;
    }

    private static String generateHard() {
        char operator = operations.charAt(random.nextInt(operations.length()));
        int version = random.nextInt(2);
        if (version == 0) {
            return generateHardParenthesis() + "" + operator + "" + generateHardParenthesis();
        } else {
            String first = generateHardParenthesis();
            Integer firstValue = parseSolution(first);
            if (firstValue < 30) {
                String second = "(" + (random.nextInt(10) + 10) + "+*".charAt(random.nextInt(2)) + first + ")";
                return "" + (random.nextInt(10) + 10) + operationsPlusMinus.charAt(random.nextInt(2)) + second;
            } else {
                String second = "(" + first + "-" + (random.nextInt(50) + 50) + ")";
                return (random.nextInt(10) + 10) + "" +  "+*".charAt(random.nextInt(2))+ "" + second;
            }
        }
    }

    private static String generateHardParenthesis () {
        int a = random.nextInt(10) + 10;
        int b = random.nextInt(10) + 10;
        char operator = operationsHard.charAt(random.nextInt(operationsHard.length()));
        if (operator == '/') {
            a *= b;
        }
        return "(" + a + "" + operator + "" + b + ")";
    }

    public static Integer parseSolution(String equation) {
        ScriptEngineManager mgr = new ScriptEngineManager();
//        for (ScriptEngineFactory factory : mgr.getEngineFactories()) {
//            System.out.println("engine name=" + factory.getEngineName());
//            System.out.println("engine version=" + factory.getEngineVersion());
//            System.out.println("language name=" + factory.getLanguageName());
//            System.out.println("extensions=" + factory.getExtensions());
//            System.out.println("language version=" + factory.getLanguageVersion());
//            System.out.println("names=" + factory.getNames());
//            System.out.println("mime types=" + factory.getMimeTypes());
//            System.out.println();
//        }
        ScriptEngine engine = mgr.getEngineByName("javascript");
        try {
            return ((Double) engine.eval(equation)).intValue();
        } catch (ScriptException e) {
            System.err.println("Error while parsing equation: " + e);
        }
        return null;
    }
}
