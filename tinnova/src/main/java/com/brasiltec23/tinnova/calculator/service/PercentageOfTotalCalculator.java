package com.brasiltec23.tinnova.calculator.service;

import com.brasiltec23.tinnova.calculator.interfaces.CalculatorBehavior;
import com.brasiltec23.tinnova.calculator.bean.Proportion;

public record PercentageOfTotalCalculator(Proportion bean) implements CalculatorBehavior {

    @Override
    public void calculate() {
        System.out.println(((bean.part() * 100) / bean.whole()) + "%");
    }
}
