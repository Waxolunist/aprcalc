package me.pietrzak.aprcalc.exponent

import me.pietrzak.aprcalc.Configuration
import spock.lang.Specification
import spock.lang.Unroll

import java.math.RoundingMode


class LogarithmSpec extends Specification {
	@Unroll
	def "ln #x = #expected"() {
		expect:
			Logarithm.apply(x).setScale(3, RoundingMode.HALF_UP) == expected
		where:
			x                || expected
			1.00             || 0.00
			Math.E           || 1.00
			Math.E.power(4)  || 4.00
			Math.E.power(-4) || -4.00
	}

	@Unroll
	def "should inverse exponential with ln function ln #x = #expected"() {
		expect:
			Logarithm.apply(x).setScale(Configuration.internalComputationScale().intdiv(4), RoundingMode.HALF_UP) == expected
		where:
			x                        || expected
			Exponential.apply(0)     || 0.00
			Exponential.apply(1)     || 1.00
			Exponential.apply(7)     || 7.00
			Exponential.apply(11)    || 11.00
			Exponential.apply(12)    || 12.00
			Exponential.apply(15)    || 15.00
			Exponential.apply(25)    || 25.00
			Exponential.apply(100)   || 100.00
			Exponential.apply(240)   || 240.00
			Exponential.apply(2400)  || 2400.00
			Exponential.apply(24000) || 24000.00
	}

	def "should fail for negative argument"() {
		when:
			Logarithm.apply(BigDecimal.ONE.negate())
		then:
			ArithmeticException exception = thrown()
			exception.getMessage() == 'Can\'t find ln for zero and negative values'
	}
}
