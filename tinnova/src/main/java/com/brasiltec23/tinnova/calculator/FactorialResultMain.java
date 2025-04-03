package com.brasiltec23.tinnova.calculator;

import com.brasiltec23.tinnova.calculator.core.Calculator;
import com.brasiltec23.tinnova.calculator.core.FactorialCalculator;

public class FactorialResultMain {

    public static void main(String[] args) {
        for(int i = 0; i <= 6; i++) {
            Calculator factorialCalculator = new FactorialCalculator(i);
            factorialCalculator.performCalculate();
        }
    }
}