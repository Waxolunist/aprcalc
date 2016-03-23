package me.pietrzak.aprcalc.exponent;

import me.pietrzak.aprcalc.root.NewtonsMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.*;
import static me.pietrzak.aprcalc.Configuration.internalComputationScale;
import static me.pietrzak.aprcalc.Configuration.terms;

public class Logarithm {
    public static BigDecimal apply(BigDecimal x) {
        if (x.compareTo(ZERO) <= 0) {
            throw new ArithmeticException("Can't find ln for zero and negative values");
        }
        //return valueOf(2).multiply(sum(x));
        return NewtonsMethod.findZero(p -> Exponential.apply(p).subtract(x), p -> Exponential.apply(p)).get();
    }

    private static BigDecimal sum(BigDecimal x) {
        BigDecimal sum = ZERO;
        BigDecimal term = x.subtract(ONE)
                .divide(
                        x.add(ONE), internalComputationScale(), RoundingMode.HALF_UP);
        for (int n = 0; n < terms(); n++) {
            sum = sum
                    .add(
                            term.pow(2 * n + 1).divide(valueOf(2 * n + 1), internalComputationScale(), ROUND_HALF_UP)
                    );
        }

        return sum;
    }
}
