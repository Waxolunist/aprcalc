package me.pietrzak.aprcalc.exponent;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static me.pietrzak.aprcalc.Configuration.terms;
import static me.pietrzak.aprcalc.Configuration.internalComputationScale;

public class Exponential {

    public static BigDecimal apply(BigDecimal x) {
        BigDecimal sum = ZERO;
        BigDecimal xPowerN = ONE;
        BigDecimal factorial = ONE;
        for (int n = 0; n < terms(); n++) {
            if(n>0) {
                xPowerN = xPowerN.multiply(x);
                factorial = factorial.multiply(valueOf(n));
            }
            sum = sum
                    .add(
                            xPowerN.divide(factorial, internalComputationScale(), ROUND_HALF_UP)
                    );
        }
        return sum;
    }
}
