package test.datatypes;


import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techventus.server.voice.datatypes.Phone;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Brett Futral @ Catalyst IT Services
 */
public class PhoneTest {
	// testPhones
	final Phone testPhone = new Phone(1, "testPhone", "+15035551212");
	final Phone testPhone1 = new Phone(2, "testPhone1", "+15035552121");
	Phone testPhone2;

	JsonArray times = new JsonArray();

	JsonObject testJSONOb = new JsonObject();
	JsonObject testJSONOb1 = new JsonObject();

	final static String RESULT_STRING = "{\"2\":{\"voicemailForwardingVerified\":false,\"weekendAllDay\":false,\"redirectToVoicemail\":false,\"smsEnabled\":false,\"customOverrideState\":0,\"type\":0,\"policyBitmask\":0,\"id\":2,\"phoneNumber\":\"+15035552121\",\"verified\":false,\"dEPRECATEDDisabled\":false,\"name\":\"testPhone1\",\"active\":false,\"enabledForOthers\":false,\"weekdayAllDay\":false,\"telephonyVerified\":false,\"behaviorOnRedirect\":0,\"scheduleSet\":false}}";

	@Before
	public void setUp() {
		Map map = Maps.newHashMap();

		map.put("id", 2);
		map.put("name", "testPhone1");
		map.put("phoneNumber", "+15035552121");
		map.put("active", true);
		map.put("behaviorOnRedirect", 1);
		map.put("carrier", "testCarrier");
		map.put("customOverrideState", 1);
		map.put("dEPRECATEDDisabled", false);
		map.put("enabledForOthers", true);
		map.put("formattedNumber", "+15035552121");
		map.put("incomingAccessNumber", "");
		map.put("policyBitmask", 1);
		map.put("redirectToVoicemail", false);
		map.put("scheduleSet", false);
		map.put("smsEnabled", true);
		map.put("telephonyVerified", true);
		map.put("type", 1);
		map.put("verified", true);
		map.put("voicemailForwardingVerified", true);
		map.put("weekdayAllDay", true);
		map.put("weekdayTimes", times);
		map.put("weekendAllDay", true);
		map.put("weekendTimes", times);

		Gson gson = new Gson();
		String raw = gson.toJson(map);
		testJSONOb1 = gson.fromJson(raw, JsonObject.class);
	}

	@Test
	public void testPhoneConstructionNull() {

		try {
			testPhone2 = new Phone(testJSONOb);
		} catch (Exception e) {
			testPhone2 = testPhone;
		}
		assertEquals(testPhone, testPhone2);
	}

	@Test
	public void testPhoneEquality() {

		try {
			testPhone2 = new Phone(testJSONOb1);
		} catch (Exception e) {
			testPhone2 = testPhone;
		}

		assertEquals(testPhone1.getId(), testPhone2.getId());
		assertEquals(testPhone1.getName(), testPhone2.getName());
		assertEquals(testPhone1.getPhoneNumber(), testPhone2.getPhoneNumber());
	}


	@Test
	public void testCreateArrayFromJsonObject() {
		final Phone[] resultArray = {testPhone1};

		testJSONOb.add("testPhone2", testJSONOb1);

		final Phone[] testArray = Phone.createArrayFromJsonObject(testJSONOb);

		assertEquals(resultArray[0].getId(), testArray[0].getId());
		assertEquals(resultArray[0].getName(), testArray[0].getName());
		assertEquals(resultArray[0].getPhoneNumber(), testArray[0].getPhoneNumber());

	}

	@Test
	public void testPhonesArrayToJsonObject() {

		final Phone[] testArray = {testPhone1};

		final String testString = Phone.phonesArrayToJsonObject(testArray).toString();

		assertTrue(testString.contains(testPhone1.getPhoneNumber()));
		assertTrue(testString.contains(String.valueOf(testPhone1.getId())));
		assertTrue(testString.contains(String.valueOf(testPhone1.getName())));

	}

	@Test
	public void testCompareToOverrideEqual() {

		final int test = testPhone.compareTo(testPhone);

		assertEquals(0, test);
	}

	@Test
	public void testCompareToOverrideGreater() {

		final int test = testPhone1.compareTo(testPhone);

		assertEquals(1, test);
	}

	@Test
	public void testCompareToOverrideLess() {

		final int test = testPhone.compareTo(testPhone1);

		assertEquals(-1, test);
	}

}
