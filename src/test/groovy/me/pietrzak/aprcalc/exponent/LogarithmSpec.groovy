package me.pietrzak.aprcalc.exponent

import spock.lang.Specification
import spock.lang.Unroll

import java.math.RoundingMode


class LogarithmSpec extends Specification {
	@Unroll
	def "ln #x = #expected"() {
		expect:
			Logarithm.apply(x).setScale(3, RoundingMode.HALF_UP) == expected
		where:
			x                    || expected
			1.00                 || 0.00
			Math.E               || 1.00
			Math.E.power(4)      || 4.00
			Math.E.power(-4)     || -4.00
	}

	@Unroll
	def "own exponential/ln functions for ln #x = #expected"() {
		expect:
			Logarithm.apply(x).setScale(3, RoundingMode.HALF_UP) == expected
		where:
			x                     || expected
			Exponential.apply(0)  || 0.00
			Exponential.apply(1)  || 1.00
			Exponential.apply(7)  || 7.00
//			Exponential.apply(15) || 15.00
//			Exponential.apply(24) || 24.00
	}
}
