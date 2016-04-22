package me.pietrzak.aprcalc

import spock.lang.Specification
import spock.lang.Unroll

import java.math.RoundingMode
import java.time.LocalDate


class BasicCalculationsSpec extends Specification {
	def "should return 0.33525 with cashFlow from wikipedia"() {
		given:
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[LocalDate.now()] = BigDecimal.valueOf(-500)
			cashFlow[LocalDate.now().plusDays(25)] = BigDecimal.valueOf(510.00)
		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result.setScale(3, RoundingMode.HALF_UP) == 33.525
	}

	def "should return valid APR for 100 euros a 30 días and los intereses serían de 28 euros"() {
		given:
			LocalDate start = LocalDate.of(2016, 1, 1)
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[start] = BigDecimal.valueOf(-100)
			cashFlow[start.plusDays(30)] = BigDecimal.valueOf(128.00)
		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result.setScale(1, RoundingMode.HALF_UP) == 1915.5
	}

	def "should return valid APR for £100 for 13 days and £110.40 repayment"() {
		given:
			LocalDate start = LocalDate.of(2016, 1, 1)
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[start] = BigDecimal.valueOf(-100)
			cashFlow[start.plusDays(13)] = BigDecimal.valueOf(110.40)

		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result.setScale(0, RoundingMode.HALF_UP) == 1509
	}

	def "should return valid APR for  100 kr for 30 days and 150 kr repayment"() {
		given:
			LocalDate start = LocalDate.of(2016, 1, 1)
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[start] = BigDecimal.valueOf(-100)
			cashFlow[start.plusDays(30)] = BigDecimal.valueOf(150.00)

		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result.setScale(1, RoundingMode.HALF_UP) == 13781.7
	}

	def "should calculate apr for cashFlow #days day"() {
		given:
			LocalDate startDate = LocalDate.of(2015, 3, 8)
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[LocalDate.of(2015, 3, 8)] = new BigDecimal("-1000.00")
			cashFlow[startDate.plusDays(days)] = new BigDecimal(totalAmount)
		when:
			BigDecimal calculatedApr = APRCalc.calculate(cashFlow)
		then:
			calculatedApr.setScale(1, RoundingMode.HALF_UP) == correctApr.setScale(1, RoundingMode.HALF_UP)
		where:
			days | totalAmount || correctApr
			1    | '1109.1'    || 2596016400742373617.4
			2    | '1109.0'    || 15849193566.3
			3    | '1109.0'    || 29286668.1
			4    | '1109.1'    || 1269236.8
			5    | '1109.1'    || 191705.7
			6    | '1109.1'    || 54314.9
			7    | '1109.1'    || 22025.9
			8    | '1129.1'    || 25363.4
			9    | '1129.1'    || 13659.2
			10   | '1129.0'    || 8281.6
			11   | '1129.0'    || 5503.8
			12   | '1129.1'    || 3917.4
			13   | '1139.1'    || 3773.3
			14   | '1139.0'    || 2876.1
			15   | '1139.0'    || 2273.6
			16   | '1139.1'    || 1851.3
			17   | '1139.1'    || 1538.4
			18   | '1139.0'    || 1300.2
			19   | '1139.0'    || 1118.6
			20   | '1139.1'    || 977.1
			21   | '1139.1'    || 861.8
			22   | '1149.0'    || 901.8
			23   | '1149.0'    || 806.3
			24   | '1149.1'    || 727.8
			25   | '1149.0'    || 659.8
			26   | '1149.0'    || 602.7
			27   | '1149.1'    || 554.6
			28   | '1149.1'    || 512.1
			29   | '1157.0'    || 526.8
			30   | '1157.0'    || 489.6
	}
}
