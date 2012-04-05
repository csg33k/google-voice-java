package test.datatypes;

import junit.framework.Assert;

import org.junit.Test;

import com.techventus.server.voice.datatypes.Greeting;

public class GreetingTest {
	
	Greeting testGreeting = new Greeting(1, "testName", "testJobberName");
	Greeting testGreeting1 = new Greeting(2, "testName", "testJobberName");

	@Test
	public void testCompareToOverrideEqual() {
		Assert.assertEquals(0, testGreeting.compareTo(testGreeting));
	}
	
	@Test
	public void testCompareToOverrideGreater() {
		Assert.assertEquals(1, testGreeting1.compareTo(testGreeting));
	}
	
	@Test
	public void testCompareToOverrideLesser() {
		Assert.assertEquals(-1, testGreeting.compareTo(testGreeting1));
	}

}
