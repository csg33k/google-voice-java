package test.datatypes;

import static org.junit.Assert.assertArrayEquals; 
import junit.framework.Assert;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.Phone;

public class PhoneTest {

	final Phone testPhone = new Phone(1, "testPhone", "5035551212");
	Phone testPhone1;
	Phone testPhone2;
	Phone testPhone3;

	@Before
	public void setUp() throws Exception {
		//empty "time" array
		JSONArray times = new JSONArray();

		//Generic Phone JSON construction
		JSONObject testJSONOb = new JSONObject();
		testJSONOb.put("id", 2);
		testJSONOb.put("name", "testPhone1");
		testJSONOb.put("phoneNumber", "+15035552121");
		testJSONOb.put("active", true);
		testJSONOb.put("behaviorOnRedirect", 1);
		testJSONOb.put("carrier", "testCarrier");
		testJSONOb.put("customOverrideState", 1);
		testJSONOb.put("dEPRECATEDDisabled", false);
		testJSONOb.put("enabledForOthers", true);
		testJSONOb.put("formattedNumber", "+15035552121");
		testJSONOb.put("incomingAccessNumber", "");
		testJSONOb.put("policyBitmask", 1);
		testJSONOb.put("redirectToVoicemail", false);
		testJSONOb.put("scheduleSet", false);
		testJSONOb.put("smsEnabled", true);
		testJSONOb.put("telephonyVerified", true);
		testJSONOb.put("type", 1);
		testJSONOb.put("verified", true);
		testJSONOb.put("voicemailForwardingVerified", true);
		testJSONOb.put("weekdayAllDay", true);
		testJSONOb.put("weekdayTimes", times);
		testJSONOb.put("weekendAllDay", true);
		testJSONOb.put("weekendTimes", times);

		//Phones (identical)
		testPhone1 = new Phone(testJSONOb);
		testPhone2 = new Phone(testJSONOb, true);

		//Added "time" to array...just to differentiate the contents from the empty array
		times.put(1);

		//Construct another Phone
		testJSONOb.put("id", 3);
		testJSONOb.put("name", "testPhone2");
		testJSONOb.put("phoneNumber", "+15035552222");
		testJSONOb.put("active", false);
		testJSONOb.put("behaviorOnRedirect", 2);
		testJSONOb.put("carrier", "testCarrier1");
		testJSONOb.put("customOverrideState", 2);
		testJSONOb.put("dEPRECATEDDisabled", true);
		testJSONOb.put("enabledForOthers", false);
		testJSONOb.put("formattedNumber", "+15035552222");
		testJSONOb.put("incomingAccessNumber", "+15035552121");
		testJSONOb.put("policyBitmask", 2);
		testJSONOb.put("redirectToVoicemail", true);
		testJSONOb.put("scheduleSet", true);
		testJSONOb.put("smsEnabled", false);
		testJSONOb.put("telephonyVerified", false);
		testJSONOb.put("type", 2);
		testJSONOb.put("verified", false);
		testJSONOb.put("voicemailForwardingVerified", false);
		testJSONOb.put("weekdayAllDay", false);
		testJSONOb.put("weekdayTimes", times);
		testJSONOb.put("weekendAllDay", false);
		testJSONOb.put("weekendTimes", times);

		//Another Phone built form above constructor
		testPhone3 = new Phone(testJSONOb, true);

	}

	@Test
	public void testCompareToOverrideEqual() {

		final int test = testPhone.compareTo(testPhone);

		Assert.assertEquals(0, test);
	}

	@Test
	public void testCompareToOverrideGreater() {

		final int test = testPhone1.compareTo(testPhone);

		Assert.assertEquals(1, test);
	}

	@Test
	public void testCompareToOverrideLess() {

		final int test = testPhone.compareTo(testPhone1);

		Assert.assertEquals(-1, test);
	}

	@Test
	public void testCompareJSONPhoneConstructorPass() {
		Assert.assertEquals(testPhone2.getId(), testPhone1.getId());
		Assert.assertEquals(testPhone2.getName(), testPhone1.getName());
		Assert.assertEquals(testPhone2.getPhoneNumber(),
				testPhone1.getPhoneNumber());
		Assert.assertEquals(testPhone2.getBehaviorOnRedirect(),
				testPhone1.getBehaviorOnRedirect());
		Assert.assertEquals(testPhone2.getCarrier(), testPhone1.getCarrier());
		Assert.assertEquals(testPhone2.getCustomOverrideState(),
				testPhone1.getCustomOverrideState());
		Assert.assertEquals(testPhone2.getFormattedNumber(),
				testPhone1.getFormattedNumber());
		Assert.assertEquals(testPhone2.getIncomingAccessNumber(),
				testPhone1.getIncomingAccessNumber());
		Assert.assertEquals(testPhone2.getPolicyBitmask(),
				testPhone1.getPolicyBitmask());
		Assert.assertEquals(testPhone2.getType(), testPhone1.getType());
		assertArrayEquals(testPhone2.getWeekdayTimes(),
				testPhone1.getWeekdayTimes());
		assertArrayEquals(testPhone2.getWeekendTimes(),
				testPhone1.getWeekendTimes());
		Assert.assertEquals(testPhone2.isActive(), testPhone1.isActive());
		Assert.assertEquals(testPhone2.isdEPRECATEDDisabled(),
				testPhone1.isdEPRECATEDDisabled());
		Assert.assertEquals(testPhone2.isEnabledForOthers(),
				testPhone1.isEnabledForOthers());
		Assert.assertEquals(testPhone2.isRedirectToVoicemail(),
				testPhone1.isRedirectToVoicemail());
		Assert.assertEquals(testPhone2.isScheduleSet(),
				testPhone1.isScheduleSet());
		Assert.assertEquals(testPhone2.isSmsEnabled(),
				testPhone1.isSmsEnabled());
		Assert.assertEquals(testPhone2.isTelephonyVerified(),
				testPhone1.isTelephonyVerified());
		Assert.assertEquals(testPhone2.isVerified(), testPhone1.isVerified());
		Assert.assertEquals(testPhone2.isVoicemailForwardingVerified(),
				testPhone1.isVoicemailForwardingVerified());
		Assert.assertEquals(testPhone2.isWeekdayAllDay(),
				testPhone1.isWeekdayAllDay());
		Assert.assertEquals(testPhone2.isWeekendAllDay(),
				testPhone1.isWeekendAllDay());

	}

	@Test
	public void testCompareJSONPhoneConstructorFail() {
		final boolean testID = (testPhone3.getId() == testPhone1.getId());
		final boolean testName = testPhone3.getName().equals(testPhone1.getName());
		final boolean testPhoneNumber = testPhone3.getPhoneNumber()
				.equals(testPhone1.getPhoneNumber());
		final boolean testBehavior = (testPhone3.getBehaviorOnRedirect() == testPhone1
				.getBehaviorOnRedirect());
		final boolean testCarrier = testPhone3.getCarrier().equals(testPhone1
				.getCarrier());
		final boolean testOverride = (testPhone3.getCustomOverrideState() == testPhone1
				.getCustomOverrideState());
		final boolean testFormattedNumber = testPhone3.getFormattedNumber()
				.equals(testPhone1.getFormattedNumber());
		final boolean testIncomingAccess = testPhone3.getIncomingAccessNumber()
				.equals(testPhone1.getIncomingAccessNumber());
		final boolean testPolicyBit = (testPhone3.getPolicyBitmask() == testPhone1
				.getPolicyBitmask());
		final boolean testType = (testPhone3.getType() == testPhone1.getType());
		final boolean testWeekDayTimes = testPhone3.getWeekdayTimes()
				.equals(testPhone1.getWeekdayTimes());
		final boolean testWeekEndTimes = testPhone3.getWeekendTimes()
				.equals(testPhone1.getWeekendTimes());
		final boolean testActive = (testPhone3.isActive() == testPhone1.isActive());
		final boolean testDEPRECATED = (testPhone3.isdEPRECATEDDisabled() == testPhone1
				.isdEPRECATEDDisabled());
		final boolean testEnabledForOthers = (testPhone3.isEnabledForOthers() == testPhone1
				.isEnabledForOthers());
		final boolean testRedirectToVoiceMail = (testPhone3.isRedirectToVoicemail() == testPhone1
				.isRedirectToVoicemail());
		final boolean testSchedule = (testPhone3.isScheduleSet() == testPhone1
				.isScheduleSet());
		final boolean testSMS = (testPhone3.isSmsEnabled() == testPhone1
				.isSmsEnabled());
		final boolean testTelephony = (testPhone3.isTelephonyVerified() == testPhone1
				.isTelephonyVerified());
		final boolean testVerified = (testPhone3.isVerified() == testPhone1
				.isVerified());
		final boolean testForwardVerified = (testPhone3
				.isVoicemailForwardingVerified() == testPhone1
				.isVoicemailForwardingVerified());
		final boolean testWeekDayAllDay = (testPhone3.isWeekdayAllDay() == testPhone1
				.isWeekdayAllDay());
		final boolean testWeekEndAllDay = (testPhone3.isWeekendAllDay() == testPhone1
				.isWeekendAllDay());

		Assert.assertEquals(false, testID);
		Assert.assertEquals(false, testName);
		Assert.assertEquals(false, testPhoneNumber);
		Assert.assertEquals(false, testBehavior);
		Assert.assertEquals(false, testCarrier);
		Assert.assertEquals(false, testOverride);
		Assert.assertEquals(false, testFormattedNumber);
		Assert.assertEquals(false, testIncomingAccess);
		Assert.assertEquals(false, testPolicyBit);
		Assert.assertEquals(false, testType);
		Assert.assertEquals(false, testWeekDayTimes);
		Assert.assertEquals(false, testWeekEndTimes);
		Assert.assertEquals(false, testActive);
		Assert.assertEquals(false, testDEPRECATED);
		Assert.assertEquals(false, testEnabledForOthers);
		Assert.assertEquals(false, testRedirectToVoiceMail);
		Assert.assertEquals(false, testSchedule);
		Assert.assertEquals(false, testSMS);
		Assert.assertEquals(false, testTelephony);
		Assert.assertEquals(false, testVerified);
		Assert.assertEquals(false, testForwardVerified);
		Assert.assertEquals(false, testWeekDayAllDay);
		Assert.assertEquals(false, testWeekEndAllDay);

	}
}
