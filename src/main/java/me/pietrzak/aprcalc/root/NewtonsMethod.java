package me.pietrzak.aprcalc.root;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class NewtonsMethod {
    private static final int maximumNumberOfIterations = 5000;
    private static final int internalComputationsScale = 20;

    private static final BigDecimal maximumRange = BigDecimal.TEN.pow(322);
    private static final BigDecimal epsilon = BigDecimal.valueOf(1E-10);

    public static Optional<BigDecimal> findZero(Function<BigDecimal, BigDecimal> function, Function<BigDecimal, BigDecimal> derivative) {
        BigDecimal x = BigDecimal.ZERO;
        int iterationsCounter = 0;
        do {
            BigDecimal derivativeAtX = derivative.apply(x);
            if (derivativeAtX.compareTo(BigDecimal.ZERO) == 0) {
                return Optional.empty();
            }
            if (x.compareTo(BigDecimal.ZERO)<0) {
                x = BigDecimal.ZERO;
            }
            if (iterationsCounter++ > maximumNumberOfIterations) {
                return Optional.empty();
            }
            if (x.compareTo(maximumRange)>0) {
                return Optional.empty();
            }
            x = x.subtract(function.apply(x).divide(derivativeAtX, internalComputationsScale, BigDecimal.ROUND_HALF_UP));
        } while (errorIsLargerThanExpected(function, x, epsilon));
        return Optional.of(x);
    }

    private static boolean errorIsLargerThanExpected(Function<BigDecimal, BigDecimal> function, BigDecimal x, BigDecimal epsilon) {
        return function.apply(x).abs().compareTo(epsilon) >= 0;
    }
}
