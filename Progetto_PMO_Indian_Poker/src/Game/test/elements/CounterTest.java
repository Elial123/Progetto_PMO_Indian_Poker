package Game.test.elements;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.project.resources.model.elements.Counter;

class CounterTest {
	private Counter counter;
	int testNumber1,
		testNumber2,
		expectedResult;
	
	@BeforeEach                                         
    void setUp() {
		counter = new Counter();
    }
	
	// test metodo dec()
	@Test
	void testDec() {
		testNumber1 = (int)(Math.random() * (5 - 1)) + 1; // [1, 5]
		testNumber2 = (int)(Math.random() * (testNumber1 - 1)) + 1; // [1, testNumber1]
		expectedResult = testNumber1 - testNumber2;
		Assert.assertEquals(expectedResult, counter.dec(testNumber1, testNumber2));
	}
	// test metodo inc()
	@Test
	void testInc() {
		testNumber1 = (int)(Math.random() * (5 - 1)) + 1; // [1, 5]
		testNumber2 = (int)(Math.random() * (testNumber1 - 1)) + 1; // [1, testNumber1]
		expectedResult = testNumber1 + testNumber2;
		Assert.assertEquals(expectedResult, counter.inc(testNumber1, testNumber2));
	}

}
