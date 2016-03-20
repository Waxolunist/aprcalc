package me.pietrzak.aprcalc
import spock.lang.Specification

import java.time.LocalDate

class CoreApiSpec extends Specification {

	def "should invoke APRcalc with empty cashFlow and return 0"() {
		given:
			Map<LocalDate, BigDecimal> cashFlow = [:]
		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result == 0
	}

	def "should return 0 if we don't charge Client with interests"() {
		given:
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[LocalDate.now().plusMonths(1)] = BigDecimal.TEN.negate()
			cashFlow[LocalDate.now().plusMonths(2)] = BigDecimal.TEN
		when:
			BigDecimal result = APRCalc.calculate(cashFlow)
		then:
			result == 0
	}

	def "should fail for not balanced flow"() {
		given:
			Map<LocalDate, BigDecimal> cashFlow = [:]
			cashFlow[LocalDate.now().plusMonths(1)] = BigDecimal.TEN
			cashFlow[LocalDate.now().plusMonths(2)] = BigDecimal.TEN
		when:
			APRCalc.calculate(cashFlow)
		then:
			thrown(UnsupportedOperationException)
	}
}
