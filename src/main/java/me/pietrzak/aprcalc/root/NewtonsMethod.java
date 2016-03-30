package me.pietrzak.aprcalc.root;

import me.pietrzak.aprcalc.Configuration;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static me.pietrzak.aprcalc.Configuration.internalComputationScale;

public class NewtonsMethod extends ApproximationMethod {
    private static final BigDecimal maximumRange = BigDecimal.TEN.pow(322);


    public static Optional<BigDecimal> findZero(Function<BigDecimal, BigDecimal> function, Function<BigDecimal, BigDecimal> derivative) {
        BigDecimal x = BigDecimal.ZERO;
        int iterationsCounter = 0;
        try {
            do {
                if (derivative.apply(x).equals(BigDecimal.ZERO)) {
                    return Optional.empty();
                }
                if (x.compareTo(BigDecimal.ZERO) < 0) {
                    x = BigDecimal.ZERO;
                }
                if (iterationsCounter++ > Configuration.maximumNumberOfIterations()) {
                    return Optional.empty();
                }
                if (x.compareTo(maximumRange) > 0) {
                    return Optional.empty();
                }
                x = x.subtract(function.apply(x).divide(derivative.apply(x), internalComputationScale(), BigDecimal.ROUND_HALF_UP));
            } while (errorIsLargerThanExpected(function, x, Configuration.epsilon()));
        } catch (ArithmeticException e) {
            return Optional.empty();
        }
        return Optional.of(x);
    }


}
