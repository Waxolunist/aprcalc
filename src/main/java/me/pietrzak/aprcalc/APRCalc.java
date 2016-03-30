package me.pietrzak.aprcalc;

import me.pietrzak.aprcalc.exponent.Power;
import me.pietrzak.aprcalc.root.BisectionMethod;
import me.pietrzak.aprcalc.root.NewtonsMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByKey;

public class APRCalc {

    public static BigDecimal calculate(LinkedHashMap<LocalDate, BigDecimal> cashFlow) {
        if (cashFlow == null) {
            throw new NullPointerException("CashFlow should be defined");
        }

        if (cashFlow.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return NewtonsMethod.findZero(
                x -> cashFlowSum(cashFlow, x),
                x -> cashFlowSumDerivative(cashFlow, x)
        ).orElseGet(() -> BisectionMethod.findZero(x -> cashFlowSum(cashFlow, x)
        ).orElseThrow(UnsupportedOperationException::new))
                .multiply(BigDecimal.valueOf(100));
    }

    private static BigDecimal cashFlowSum(LinkedHashMap<LocalDate, BigDecimal> cashFlow, BigDecimal x) {
        LocalDate startDate = cashFlow.entrySet().stream().min(Comparator.comparing(Map.Entry::getKey)).get().getKey();

        return cashFlow.entrySet().stream()
                .map(entry -> discountPayment(startDate, entry, x))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal discountPayment(LocalDate startDate, Map.Entry<LocalDate, BigDecimal> entry, BigDecimal x) {
        return entry.getValue()
                .multiply(Power.apply(x.add(BigDecimal.ONE), yearFraction(startDate, entry)));
    }

    private static BigDecimal cashFlowSumDerivative(LinkedHashMap<LocalDate, BigDecimal> cashFlow, BigDecimal x) {
        LocalDate startDate = cashFlow.entrySet().stream()
                .min(comparingByKey(LocalDate::compareTo))
                .get().getKey();
        return cashFlow.entrySet().stream().map(
                entry -> entry.getValue()
                        .multiply(
                                Power.apply(x.add(BigDecimal.ONE), yearFraction(startDate, entry).subtract(BigDecimal.ONE))
                        )
                        .multiply(yearFraction(startDate, entry)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal yearFraction(LocalDate startDate, Map.Entry<LocalDate, BigDecimal> entry) {
        return BigDecimal.valueOf(-days(startDate, entry)).divide(
                BigDecimal.valueOf(365.0), Configuration.internalComputationScale(),
                BigDecimal.ROUND_HALF_UP);
    }

    private static long days(LocalDate startDate, Map.Entry<LocalDate, BigDecimal> entry) {
        return ChronoUnit.DAYS.between(startDate, entry.getKey());
    }
}
