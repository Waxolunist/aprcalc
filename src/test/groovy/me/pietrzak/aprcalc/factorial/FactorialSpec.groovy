package me.pietrzak.aprcalc.factorial

import spock.lang.Specification
import spock.lang.Unroll


class FactorialSpec extends Specification {
	@Unroll
	def "#expected = #n!"() {
		expect:
			expected == Factorial.apply(n)
		where:
			n || expected
			1 || 1
			2 || 2
			3 || 6
			4 || 24
			5 || 120
	}

}
