package com.brasiltec23.tinnova.calculator.core;

import com.brasiltec23.tinnova.calculator.bean.Proportion;
import com.brasiltec23.tinnova.calculator.service.PercentageOfTotalCalculator;

public class PercentageCalculator extends Calculator {

    public PercentageCalculator(Proportion bean) {
        calculatorBehavior = new PercentageOfTotalCalculator(bean);
    }
}
