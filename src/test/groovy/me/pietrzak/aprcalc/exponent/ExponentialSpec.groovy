package me.pietrzak.aprcalc.exponent

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ExponentialSpec extends Specification {
	def "should provide valid #y = exp( #x )"() {
		expect:
			Exponential.apply(x).doubleValue() == BigDecimal.valueOf(y)
		where:
			x   || y
			0   || 1
			1   || Math.E
			2   || Math.exp(2)
			20  || Math.exp(20)
			200 || Math.exp(200)
	}
}
