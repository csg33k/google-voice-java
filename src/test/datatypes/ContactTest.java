package test.datatypes;

import junit.framework.Assert;

import org.junit.Test;

import com.techventus.server.voice.datatypes.Contact;

public class ContactTest {
	
	private static String nodata = null;
	private static String testName = "testName";
	private static String testname1 = "testName1";
	private static String testID = "testID";
	private static String testNumber = "testNumber";
	private static String testNumber1 = "testNumber1";
	private static String testURL = "testURL";
	
	Contact testContact = new Contact(testName, testID, testNumber, testURL);
	Contact testContact1 = new Contact(testname1, testID, testNumber1, testURL);


	@Test
	public void testToStringOverrideFull() {
		Assert.assertEquals("{id=testID;name=testName;number=testNumber;imageUrl=testURL;}", testContact.toString());
	}
	
	@Test
	public void testToStringOverrideMinusID() {
		
		testContact.setId(nodata);
		Assert.assertEquals("{name=testName;number=testNumber;imageUrl=testURL;}", testContact.toString());
	}
	
	@Test
	public void testToStringOverrideMinusName() {
		
		testContact.setName(nodata);
		Assert.assertEquals("{id=testID;number=testNumber;imageUrl=testURL;}", testContact.toString());

	}
	
	@Test
	public void testToStringOverrideMinusNumber() {
		
		testContact.setNumber(nodata);
		Assert.assertEquals("{id=testID;name=testName;imageUrl=testURL;}", testContact.toString());

	}
	
	@Test
	public void testToStringOverrideMinusURL() {
		
		testContact.setImageUrl(nodata);
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
