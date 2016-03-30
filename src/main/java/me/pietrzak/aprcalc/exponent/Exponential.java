package me.pietrzak.aprcalc.exponent;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static me.pietrzak.aprcalc.Configuration.terms;
import static me.pietrzak.aprcalc.Configuration.internalComputationScale;

public class Exponential {

    public static BigDecimal apply(BigDecimal x) {
        if (x.compareTo(BigDecimal.TEN) > 0) {
            BigDecimal xp = apply(x.divide(valueOf(16), internalComputationScale(), ROUND_HALF_UP));
            for (int i = 0; i < 4; i++) {
                xp = xp.multiply(xp);
            }
            return xp;
        }
        return series(x);
    }

    private static BigDecimal series(BigDecimal x) {
        BigDecimal sum = ZERO;
        BigDecimal xPowerN = ONE;
        BigDecimal factorial = ONE;
        for (int n = 0; n < terms(); n++) {
            if (n > 0) {
                xPowerN = xPowerN.multiply(x);
                factorial = factorial.multiply(valueOf(n));
            }
            sum = sum.add(xPowerN.divide(factorial, internalComputationScale(), ROUND_HALF_UP));
        }
        return sum;
    }
}
