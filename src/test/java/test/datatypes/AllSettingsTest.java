package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.techventus.server.voice.datatypes.AllSettings;
import com.techventus.server.voice.datatypes.DisabledId;
import com.techventus.server.voice.datatypes.Greeting;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Phone;
import com.techventus.server.voice.datatypes.Setting;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class AllSettingsTest {
	// testAllSettings(s)
	AllSettings testAllSettings;
	AllSettings testAllSettings1;
	// Params for testAllSettings(s)
	Setting testSetting;
	Greeting testGreeting;
	Group testGroup;
	Phone testPhone;
	Phone testPhone1;
	Phone testPhone2;


	final JsonObject emptyJsonObject = new JsonObject();
	JsonObject testJSONGroupOb = new JsonObject();
	JsonObject testJSONGroupOb1 = new JsonObject();
	JsonObject jsonSetting = new JsonObject();
	JsonObject testJSONPhoneOb = new JsonObject();
	JsonObject jsonPhone = new JsonObject();

	final JsonArray emptyJsonArray = new JsonArray();
	JsonArray testGroupList = new JsonArray();
	JsonArray testGroupArray = new JsonArray();
	JsonArray testPhoneList = new JsonArray();

	final DisabledId testDisabledID = new DisabledId("3", true);
	final DisabledId testDisabledID1 = new DisabledId("4", true);

	DisabledId[] testDisArray = { testDisabledID, testDisabledID1 };

	private final static String NAME = "name";

	@Before
	public void setUp() throws Exception {

		// Build groupList

		testGroupList.add(new JsonPrimitive("testID"));

		// Build groups
		testJSONGroupOb.addProperty("id", "testID");
		testJSONGroupOb.addProperty(NAME, "testName");
		testJSONGroupOb.addProperty("isCustomForwarding", false);
		testJSONGroupOb.addProperty("isCustomGreeting", false);
		testJSONGroupOb.addProperty("isCustomDirectConnect", false);
		testJSONGroupOb.addProperty("directConnect", false);
		testJSONGroupOb.addProperty("greetingId", 1);
		testJSONGroupOb.add("disabledForwardingIds", emptyJsonObject);
		testGroup = new Group(testJSONGroupOb);
		// Insert testGroup into an array
		Gson gson = new Gson();
		testGroupArray.add(gson.toJsonTree(testGroup).getAsJsonObject());
		// And move it into a new Object
		testJSONGroupOb1.add("testID", testJSONGroupOb);

		// Build Setting
		jsonSetting.add("activeForwardingIds", emptyJsonArray);
		jsonSetting.addProperty("baseUrl", "testURL");
		jsonSetting.addProperty("credits", 1);
		jsonSetting.addProperty("defaultGreetingId", 1);
		jsonSetting.add("didInfos", emptyJsonArray);
		jsonSetting.addProperty("directConnect", true);
		jsonSetting.add("disabledIdMap", emptyJsonObject);
		jsonSetting.addProperty("doNotDisturb", false);
		jsonSetting.add("emailAddresses", emptyJsonObject);
		jsonSetting.addProperty("emailNotificationActive", true);
		jsonSetting.addProperty("emailNotificationAddress", "testEmailAddress");
		jsonSetting.add("greetings", emptyJsonObject);
		jsonSetting.add("groupList", testGroupList);
		jsonSetting.add("groups", testJSONGroupOb1);
		jsonSetting.addProperty("language", "English");
		jsonSetting.addProperty("primaryDid", "testDiD");
		jsonSetting.addProperty("screenBehavior", 1);
		jsonSetting.addProperty("showTranscripts", false);
		jsonSetting.add("smsNotifications", emptyJsonArray);
		jsonSetting.addProperty("smsToEmailActive", true);
		jsonSetting.addProperty("smsToEmailSubject", false);
		jsonSetting.addProperty("spam", "spam");
		jsonSetting.addProperty("timezone", "testTimeZone");
		jsonSetting.addProperty("useDidAsCallerId", true);
		jsonSetting.addProperty("useDidAsSource", false);
		testSetting = new Setting(jsonSetting);
		testSetting.setmDisabledIdList(null);
		final JsonObject testJSONSetting = testSetting.toJsonObject();
		// AddDisabledIDs
		testSetting.setmDisabledIdList(testDisArray);
		final JsonObject testJSONSetting1 = testSetting.toJsonObject();

		// Build PhoneList
		testPhoneList.add(new JsonPrimitive(1));
		testPhoneList.add(new JsonPrimitive(2));
		testPhoneList.add(new JsonPrimitive(3));

		// Build Phones
		// Build testPhone
		testJSONPhoneOb.addProperty("id", 1);
		testJSONPhoneOb.addProperty(NAME, "testPhone");
		testJSONPhoneOb.addProperty("phoneNumber", "+15035552121");
		testJSONPhoneOb.addProperty("active", true);
		testJSONPhoneOb.addProperty("behaviorOnRedirect", 1);
		testJSONPhoneOb.addProperty("carrier", "testCarrier");
		testJSONPhoneOb.addProperty("customOverrideState", 1);
		testJSONPhoneOb.addProperty("dEPRECATEDDisabled", false);
		testJSONPhoneOb.addProperty("enabledForOthers", true);
		testJSONPhoneOb.addProperty("formattedNumber", "+15035552121");
		testJSONPhoneOb.addProperty("incomingAccessNumber", "");
		testJSONPhoneOb.addProperty("policyBitmask", 1);
		testJSONPhoneOb.addProperty("redirectToVoicemail", false);
		testJSONPhoneOb.addProperty("scheduleSet", false);
		testJSONPhoneOb.addProperty("smsEnabled", true);
		testJSONPhoneOb.addProperty("telephonyVerified", true);
		testJSONPhoneOb.addProperty("type", 1);
		testJSONPhoneOb.addProperty("verified", true);
		testJSONPhoneOb.addProperty("voicemailForwardingVerified", true);
		testJSONPhoneOb.addProperty("weekdayAllDay", true);
		testJSONPhoneOb.add("weekdayTimes", emptyJsonArray);
		testJSONPhoneOb.addProperty("weekendAllDay", true);
		testJSONPhoneOb.add("weekendTimes", emptyJsonArray);
		// testPhone
		testPhone = new Phone(testJSONPhoneOb);
		final JsonObject testJSONPhone = testPhone.toJsonObject();
		// Build testPhone1
		testJSONPhoneOb.addProperty("id", 2);
		testJSONPhoneOb.addProperty(NAME, "testPhone1");
		testJSONPhoneOb.addProperty("phoneNumber", "+15035551212");
		testJSONPhoneOb.addProperty("formattedNumber", "+15035551212");
		// testPhone
		testPhone1 = new Phone(testJSONPhoneOb);
		final JsonObject testJSONPhone1 = testPhone1.toJsonObject();
		// Build testPhone2
		testJSONPhoneOb.addProperty("id", 3);
		testJSONPhoneOb.addProperty(NAME, "testPhone2");
		testJSONPhoneOb.addProperty("phoneNumber", "+15035552123");
		testJSONPhoneOb.addProperty("formattedNumber", "+15035552123");
		// testPhone
		testPhone2 = new Phone(testJSONPhoneOb);
		final JsonObject testJSONPhone2 = testPhone2.toJsonObject();
		// And addProperty them in an Object
		jsonPhone.add("1", testJSONPhone);
		jsonPhone.add("2", testJSONPhone1);
		jsonPhone.add("3", testJSONPhone2);

		// BuildAllSettings
		final JsonObject jsAllSettings = new JsonObject();
		jsAllSettings.add("phoneList", testPhoneList);
		jsAllSettings.add("phones", jsonPhone);
		jsAllSettings.add("settings", testJSONSetting);

		// BuildAllSettings1
		final JsonObject jsAllSettings1 = new JsonObject();
		jsAllSettings1.add("phoneList", testPhoneList);
		jsAllSettings1.add("phones", jsonPhone);
		jsAllSettings1.add("settings", testJSONSetting1);

		gson.toJsonTree(jsAllSettings);

		testAllSettings = new AllSettings(jsAllSettings);
		testAllSettings1 = new AllSettings(jsAllSettings1);

	}

	@Ignore
	@Test
	public void testIsPhoneDisabledNullList() {

		final boolean test = testAllSettings.isPhoneDisabled(1);

		assertEquals(false, test);

	}

	@Ignore
	@Test
	public void testIsPhoneDisabledFalse() {

		final boolean test = testAllSettings1.isPhoneDisabled(2);

		assertEquals(false, test);
	}

	@Ignore
	@Test
	public void testIsPhoneDisabledTrue() {

		final boolean test = testAllSettings1.isPhoneDisabled(3);

		assertEquals(true, test);
	}

	@Ignore
	@Test
	public void testGetPhoneListAsList() {

		final List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(2);
		testList.add(3);

		assertEquals(testList, testAllSettings.getPhoneListAsList());

	}

}
