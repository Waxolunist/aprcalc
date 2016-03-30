package me.pietrzak.aprcalc.exponent;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static me.pietrzak.aprcalc.Configuration.internalComputationScale;
import static me.pietrzak.aprcalc.Configuration.terms;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Logarithm {
    private static BigDecimal expTen = Exponential.apply(BigDecimal.TEN);
    private static BigDecimal e = valueOf(Math.E);
    private static BigDecimal two = valueOf(2);

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
        return y.compareTo(e) > 0;
    }

    private static BigDecimal seriesApproximation(BigDecimal x) {
        return two.multiply(seriesSum(x));
    }

    private static BigDecimal seriesSum(BigDecimal x) {
        BigDecimal sum = ZERO;
        BigDecimal term = x.subtract(ONE).divide(x.add(ONE), internalComputationScale(), RoundingMode.HALF_UP);
        BigDecimal th = term;
        BigDecimal sh = th.multiply(th);
        for (int n = 0; n < terms(); n++) {
            
            sum = sum
                    .add(
                            th.divide(valueOf(2 * n + 1), internalComputationScale(), ROUND_HALF_UP)
                    );
            th = th.multiply(sh);
        }

        return sum;
    }
}
