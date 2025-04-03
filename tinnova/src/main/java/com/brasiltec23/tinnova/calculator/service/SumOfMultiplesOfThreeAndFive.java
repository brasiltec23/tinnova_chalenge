package com.brasiltec23.tinnova.calculator.service;

import com.brasiltec23.tinnova.calculator.interfaces.CalculatorBehavior;

import java.util.stream.IntStream;

public record SumOfMultiplesOfThreeAndFive(int value) implements CalculatorBehavior {

    @Override
    public void calculate() {

        System.out.println(IntStream.range(1, value)
                .filter(n -> n % 3 == 0 || n % 5 == 0)
                .sum());
    }
}
