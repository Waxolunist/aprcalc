package me.pietrzak.aprcalc;

import me.pietrzak.aprcalc.exponent.Power;

import java.math.BigDecimal;

public class Configuration {
    public static int internalComputationScale() {
        return 40;
    }

    public static int terms() {
        return 100;
    }

    public static int maximumNumberOfIterations() {
        return 100;
    }

    public static BigDecimal epsilon() {
        return Power.apply(BigDecimal.valueOf(2), BigDecimal.valueOf(epsilonExp()).negate());
    }

    private static int epsilonExp() {
        return 20;
    }

    public static BigDecimal maximumRange() {
        return BigDecimal.valueOf(2).pow(100);
    }
}
