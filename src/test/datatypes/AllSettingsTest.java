package test.datatypes;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.AllSettings;
import com.techventus.server.voice.datatypes.DisabledId;
import com.techventus.server.voice.datatypes.Greeting;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Phone;
import com.techventus.server.voice.datatypes.Setting;

/**
 * 
 * @author bFutral
 *
 */
public class AllSettingsTest {
	//testAllSettings(s)
	AllSettings testAllSettings;
	AllSettings testAllSettings1;
	//Params for testAllSettings(s) 
	Setting testSetting;
	DisabledId testDisabledID;
	Greeting testGreeting;
	Group testGroup;
	Phone testPhone;
	Phone testPhone1;
	Phone testPhone2;

	@Before
	public void setUp() throws Exception {
		// Empty Objects for Params
		final JSONArray emptyJSONArray = new JSONArray();
		final JSONObject emptyJSONObject = new JSONObject();

		// BuildDisabledIDArray
		final DisabledId testDisabledID = new DisabledId("3", true);
		DisabledId[] testDisArray = { testDisabledID };

		// Build groupList
		JSONArray testGroupList = new JSONArray();
		testGroupList.put("testID");

		// Build groups
		JSONObject testJSONGroupOb = new JSONObject();
		testJSONGroupOb.put("id", "testID");
		testJSONGroupOb.put("name", "testName");
		testJSONGroupOb.put("isCustomForwarding", false);
		testJSONGroupOb.put("isCustomGreeting", false);
		testJSONGroupOb.put("isCustomDirectConnect", false);
		testJSONGroupOb.put("directConnect", false);
		testJSONGroupOb.put("greetingId", 1);
		testJSONGroupOb.put("disabledForwardingIds", emptyJSONObject);
		testGroup = new Group(testJSONGroupOb);
		// Insert testGroup into an array
		JSONArray testGroupArray = new JSONArray();
		testGroupArray.put(testGroup);
		// And move it into a new Object
		JSONObject testJSONGroupOb1 = new JSONObject();
		testJSONGroupOb1.put("testID", testJSONGroupOb);

		// Build Setting
		JSONObject jsonSetting = new JSONObject();
		jsonSetting.put("activeForwardingIds", emptyJSONArray);
		jsonSetting.put("baseUrl", "testURL");
		jsonSetting.put("credits", 1);
		jsonSetting.put("defaultGreetingId", 1);
		jsonSetting.put("didInfos", emptyJSONArray);
		jsonSetting.put("directConnect", true);
		jsonSetting.put("disabledIdMap", emptyJSONObject);
		jsonSetting.put("doNotDisturb", false);
		jsonSetting.put("emailAddresses", emptyJSONObject);
		jsonSetting.put("emailNotificationActive", true);
		jsonSetting.put("emailNotificationAddress", "testEmailAddress");
		jsonSetting.put("greetings", emptyJSONObject);
		jsonSetting.put("groupList", testGroupList);
		jsonSetting.put("groups", testJSONGroupOb1);
		jsonSetting.put("language", "English");
		jsonSetting.put("primaryDid", "testDiD");
		jsonSetting.put("screenBehavior", 1);
		jsonSetting.put("showTranscripts", false);
		jsonSetting.put("smsNotifications", emptyJSONArray);
		jsonSetting.put("smsToEmailActive", true);
		jsonSetting.put("smsToEmailSubject", false);
		jsonSetting.put("spam", "spam");
		jsonSetting.put("timezone", "testTimeZone");
		jsonSetting.put("useDidAsCallerId", true);
		jsonSetting.put("useDidAsSource", false);
		testSetting = new Setting(jsonSetting);
		JSONObject testJSONSetting = testSetting.toJsonObject();
		// AddDisabledIDs
		testSetting.setmDisabledIdList(testDisArray);
		JSONObject testJSONSetting1 = testSetting.toJsonObject();

		// Build PhoneList
		JSONArray testPhoneList = new JSONArray();
		testPhoneList.put(1);
		testPhoneList.put(2);
		testPhoneList.put(3);

		// Build Phones
		// Build testPhone
		JSONObject testJSONPhoneOb = new JSONObject();
		testJSONPhoneOb.put("id", 1);
		testJSONPhoneOb.put("name", "testPhone");
		testJSONPhoneOb.put("phoneNumber", "+15035552121");
		testJSONPhoneOb.put("active", true);
		testJSONPhoneOb.put("behaviorOnRedirect", 1);
		testJSONPhoneOb.put("carrier", "testCarrier");
		testJSONPhoneOb.put("customOverrideState", 1);
		testJSONPhoneOb.put("dEPRECATEDDisabled", false);
		testJSONPhoneOb.put("enabledForOthers", true);
		testJSONPhoneOb.put("formattedNumber", "+15035552121");
		testJSONPhoneOb.put("incomingAccessNumber", "");
		testJSONPhoneOb.put("policyBitmask", 1);
		testJSONPhoneOb.put("redirectToVoicemail", false);
		testJSONPhoneOb.put("scheduleSet", false);
		testJSONPhoneOb.put("smsEnabled", true);
		testJSONPhoneOb.put("telephonyVerified", true);
		testJSONPhoneOb.put("type", 1);
		testJSONPhoneOb.put("verified", true);
		testJSONPhoneOb.put("voicemailForwardingVerified", true);
		testJSONPhoneOb.put("weekdayAllDay", true);
		testJSONPhoneOb.put("weekdayTimes", emptyJSONArray);
		testJSONPhoneOb.put("weekendAllDay", true);
		testJSONPhoneOb.put("weekendTimes", emptyJSONArray);
		// testPhone
		testPhone = new Phone(testJSONPhoneOb);
		JSONObject testJSONPhone = testPhone.toJsonObject();
		// Build testPhone1
		testJSONPhoneOb.put("id", 2);
		testJSONPhoneOb.put("name", "testPhone1");
		testJSONPhoneOb.put("phoneNumber", "+15035551212");
		testJSONPhoneOb.put("formattedNumber", "+15035551212");
		// testPhone
		testPhone1 = new Phone(testJSONPhoneOb);
		JSONObject testJSONPhone1 = testPhone1.toJsonObject();
		// Build testPhone2
		testJSONPhoneOb.put("id", 3);
		testJSONPhoneOb.put("name", "testPhone2");
		testJSONPhoneOb.put("phoneNumber", "+15035552123");
		testJSONPhoneOb.put("formattedNumber", "+15035552123");
		// testPhone
		testPhone2 = new Phone(testJSONPhoneOb);
		JSONObject testJSONPhone2 = testPhone2.toJsonObject();
		// And put them in an Object
		JSONObject jsonPhone = new JSONObject();
		jsonPhone.put("1", testJSONPhone);
		jsonPhone.put("2", testJSONPhone1);
		jsonPhone.put("3", testJSONPhone2);

		// BuildAllSettings
		JSONObject jsonAllSettingsString = new JSONObject();
		jsonAllSettingsString.put("phoneList", testPhoneList);
		jsonAllSettingsString.put("phones", jsonPhone);
		jsonAllSettingsString.put("settings", testJSONSetting);

		// BuildAllSettings1
		JSONObject jsonAllSettingsString1 = jsonAllSettingsString;
		jsonAllSettingsString1.put("settings", testJSONSetting1);

		testAllSettings = new AllSettings(jsonAllSettingsString.toString());
		testAllSettings1 = new AllSettings(jsonAllSettingsString1.toString());

	}

	@Test
	public void testIsPhoneDisabledNullList() throws JSONException {

		final boolean test = testAllSettings.isPhoneDisabled(1);

		Assert.assertEquals(false, test);

	}
	
	@Test
	public void testIsPhoneDisabledFalse() throws JSONException {

		final boolean test = testAllSettings1.isPhoneDisabled(2);

		Assert.assertEquals(false, test);
	}
	
	@Test
	public void testIsPhoneDisabledTrue() throws JSONException {

		final boolean test = testAllSettings1.isPhoneDisabled(3);

		Assert.assertEquals(true, test);
	}

}
