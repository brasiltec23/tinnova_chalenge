package com.brasiltec23.tinnova.calculator.core;

import com.brasiltec23.tinnova.calculator.service.SumOfMultiplesOfThreeAndFive;

public class SumCalculator extends Calculator {

    public SumCalculator(int value) {
        calculatorBehavior = new SumOfMultiplesOfThreeAndFive(value);
    }
}
