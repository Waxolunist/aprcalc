package me.pietrzak.aprcalc.exponent;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.*;
import static me.pietrzak.aprcalc.Configuration.internalComputationScale;
import static me.pietrzak.aprcalc.Configuration.terms;

public class Logarithm {
    private static BigDecimal expTen = Exponential.apply(BigDecimal.TEN);

    public static BigDecimal apply(BigDecimal x) {
        if (x.compareTo(ZERO) <= 0) {
            throw new ArithmeticException("Can't find ln for zero and negative values");
        }

        if (largerThanExpTen(x)) {
            return apply(x.divide(expTen, internalComputationScale(), ROUND_HALF_UP)).add(BigDecimal.TEN);
        }

        return compute(x);
    }

    private static boolean largerThanExpTen(BigDecimal x) {
        return x.compareTo(expTen) > 0;
    }

    private static BigDecimal compute(BigDecimal x) {
        BigDecimal approx = valueOf(Math.log(x.doubleValue()));
        if (largerThanE(approx)) {
            return approx.add(seriesApproximation(x.divide(Exponential.apply(approx), internalComputationScale(), ROUND_HALF_UP)));
        }
        return seriesApproximation(x);
    }

    private static boolean largerThanE(BigDecimal y) {
        return y.compareTo(valueOf(Math.E)) > 0;
    }

    private static BigDecimal seriesApproximation(BigDecimal x) {
        return valueOf(2).multiply(seriesSum(x));
    }

    private static BigDecimal seriesSum(BigDecimal x) {
        BigDecimal sum = ZERO;
        BigDecimal term = x.subtract(ONE).divide(x.add(ONE), internalComputationScale(), RoundingMode.HALF_UP);
        for (int n = 0; n < terms(); n++) {
            sum = sum
                    .add(
                            term.pow(2 * n + 1).divide(valueOf(2 * n + 1), internalComputationScale(), ROUND_HALF_UP)
                    );
        }

        return sum;
    }
}
