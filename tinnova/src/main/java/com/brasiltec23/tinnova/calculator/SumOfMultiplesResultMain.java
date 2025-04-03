package com.brasiltec23.tinnova.calculator;

import com.brasiltec23.tinnova.calculator.core.Calculator;
import com.brasiltec23.tinnova.calculator.core.SumCalculator;

public class SumOfMultiplesResultMain {

    public static void main(String[] args) {
        Calculator sumCalculator = new SumCalculator(10);
        System.out.println("A soma desses múltiplos é de:");
        sumCalculator.performCalculate();
    }
}
