package com.brasiltec23.tinnova.calculator.core;

import com.brasiltec23.tinnova.calculator.interfaces.CalculatorBehavior;

public abstract class Calculator {

    CalculatorBehavior calculatorBehavior;

    public Calculator() {

    }

    public void performCalculate() {
        calculatorBehavior.calculate();
    }
}
