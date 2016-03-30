package me.pietrzak.aprcalc;

import java.math.BigDecimal;

public class Configuration {
    private static int epsilonExp = 21;
    private static BigDecimal epsilon = BigDecimal.ONE.divide(BigDecimal.TEN.pow(epsilonExp));
    
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
        return epsilon;
    }

    private static int epsilonExp() {
        return epsilonExp;
    }

    public static BigDecimal maximumRange() {
        return BigDecimal.valueOf(2).pow(100);
    }
}
