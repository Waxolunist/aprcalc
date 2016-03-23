package me.pietrzak.aprcalc.factorial;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

public class Factorial {
    public static BigDecimal apply(int i) {
        if (i <= 1) {
            return ONE;
        }
        BigDecimal factorial = ONE;
        for (int n = 2; n <= i; n++) {
            factorial = factorial.multiply(valueOf(n));
        }
        return factorial;
    }
}
