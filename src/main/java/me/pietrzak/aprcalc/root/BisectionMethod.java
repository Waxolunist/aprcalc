package me.pietrzak.aprcalc.root;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static me.pietrzak.aprcalc.Configuration.*;

public class BisectionMethod extends ApproximationMethod {
    public static Optional<BigDecimal> findZero(Function<BigDecimal, BigDecimal> function) {
        BigDecimal left = BigDecimal.ZERO;
        BigDecimal right = maximumRange();
        BigDecimal medium;
        int iterationsCounter = 0;
        do {
            medium = left.add(right)
                    .divide(BigDecimal.valueOf(2), internalComputationScale(), BigDecimal.ROUND_HALF_UP);
            if (iterationsCounter++ > maximumNumberOfIterations()) {
                return Optional.empty();
            }
            if (function.apply(left).multiply(function.apply(medium)).compareTo(BigDecimal.ZERO) < 0) {
                right = medium;
            }
            if (function.apply(medium).multiply(function.apply(right)).compareTo(BigDecimal.ZERO) < 0) {
                left = medium;
            }

        } while (errorIsLargerThanExpected(function, medium, epsilon()));

        return Optional.of(medium);
    }
}