package com.brasiltec23.tinnova.calculator.core;

import com.brasiltec23.tinnova.calculator.service.FactorialCalculatorOfANumber;

public class FactorialCalculator extends Calculator {

    public FactorialCalculator(int value) {
        calculatorBehavior = new FactorialCalculatorOfANumber(value);
    }
}
