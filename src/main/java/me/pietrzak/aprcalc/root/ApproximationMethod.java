package me.pietrzak.aprcalc.root;

import java.math.BigDecimal;
import java.util.function.Function;

public class ApproximationMethod {
    public static boolean errorIsLargerThanExpected(Function<BigDecimal, BigDecimal> function, BigDecimal x, BigDecimal epsilon) {
        return function.apply(x).abs().compareTo(epsilon) >= 0;
    }
}
