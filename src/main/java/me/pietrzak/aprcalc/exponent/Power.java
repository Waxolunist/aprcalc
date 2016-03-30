package me.pietrzak.aprcalc.exponent;

import java.math.BigDecimal;

public class Power {
    public static BigDecimal apply(BigDecimal i, BigDecimal j) {
        if (i.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        if (i.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Can't power values smaller than 0");
        }
        return Exponential.apply(j.multiply(Logarithm.apply(i)));
    }
}
