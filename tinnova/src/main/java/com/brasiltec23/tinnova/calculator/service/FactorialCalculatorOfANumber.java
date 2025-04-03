package com.brasiltec23.tinnova.calculator.service;

import com.brasiltec23.tinnova.calculator.interfaces.CalculatorBehavior;

import java.util.stream.IntStream;

public record FactorialCalculatorOfANumber(int value) implements CalculatorBehavior {

    @Override
    public void calculate() {

        System.out.println(value + "! = " +
                IntStream.rangeClosed(1, value)
                        .reduce(1, (a, b) -> a * b));
    }
}