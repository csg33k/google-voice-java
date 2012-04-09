package test.datatypes;

import junit.framework.Assert;

import org.junit.Test;

import com.techventus.server.voice.datatypes.Contact;

public class ContactTest {
	
	//Variables for Contact Object Construction
	//noData is replacement variable to test toString override.
	private static String noData = null;
	private static String testName = "testName";
	private static String testName1 = "testName1";
	private static String testID = "testID";
	private static String testNumber = "testNumber";
	private static String testNumber1 = "testNumber1";
	private static String testURL = "testURL";
	
	//test Objects
	Contact testContact = new Contact(testName, testID, testNumber, testURL);
	Contact testContact1 = new Contact(testName1, testID, testNumber1, testURL);


	@Test
	public void testToStringOverrideFull() {
		Assert.assertEquals("{id=testID;name=testName;number=testNumber;imageUrl=testURL;}", testContact.toString());
	}
	
	@Test
	public void testToStringOverrideMinusID() {
		
		testContact.setId(noData);
		Assert.assertEquals("{name=testName;number=testNumber;imageUrl=testURL;}", testContact.toString());
	}
	
	@Test
	public void testToStringOverrideMinusName() {
		
		testContact.setName(noData);
		Assert.assertEquals("{id=testID;number=testNumber;imageUrl=testURL;}", testContact.toString());

	}
	
	@Test
	public void testToStringOverrideMinusNumber() {
		
		testContact.setNumber(noData);
		Assert.assertEquals("{id=testID;name=testName;imageUrl=testURL;}", testContact.toString());

	}
	
	@Test
	public void testToStringOverrideMinusURL() {
		
		testContact.setImageUrl(noData);
		Assert.assertEquals("{id=testID;name=testName;number=testNumber;}", testContact.toString());

	}
	
	@Test
	public void testCompareToOverrideSameNameAndNumber() {
		Assert.assertEquals(0, testContact.compareTo(testContact));
	}
	
	@Test
	public void testCompareToOverrideDifferentNameAndNumber() {
		Assert.assertEquals(1, testContact.compareTo(testContact1));
	}
	
	@Test
	public void testCompaerToOverrideSameNameDifferentNumber() {
		testContact1.setName(testName);
		Assert.assertEquals(1, testContact.compareTo(testContact1));
	}
	

}
