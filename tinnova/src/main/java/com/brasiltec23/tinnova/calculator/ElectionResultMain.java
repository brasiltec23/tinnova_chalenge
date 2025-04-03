package com.brasiltec23.tinnova.calculator;

import com.brasiltec23.tinnova.calculator.core.Calculator;
import com.brasiltec23.tinnova.calculator.core.PercentageCalculator;
import com.brasiltec23.tinnova.calculator.bean.Proportion;

public class ElectionResultMain {

    public static void main(String[] args) {

        final int total = 1000;
        showResult("O percentual de votos válidos em relação ao total de eleitores:",
                new Proportion(800, total));
        showResult("O percentual de brancos em relação ao total de eleitores:",
                new Proportion(150, total));
        showResult("O percentual de nulos em relação ao total de eleitores:",
                new Proportion(50, total));
    }

    public static void showResult(String message, Proportion bean) {

        Calculator percentageCalculator =
                new PercentageCalculator(bean);

        System.out.println(message);
        percentageCalculator.performCalculate();
        System.out.println("");
    }
}
