/**
 *
 */
package test.datatypes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledId;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class SettingTest {

	Setting testSetting;
	Group testGroup;
	String resultString;

	final static String DIRECT_CONNECT = "directConnect";
	final static String A_F_IDS = "activeForwardingIds";
	final static String BASE_URL = "baseUrl";
	final static String TEST_URL = "testURL";
	final static String CREDITS = "credits";
	final static String DFID = "defaultGreetingId";
	final static String DID_INFOS = "didInfos";
	final static String DID_MAP = "disabledIdMap";
	final static String D_N_D = "doNotDisturb";
	final static String EMAIL_ADD = "emailAddresses";
	final static String EMAIL_NOTE_ACTIVE = "emailNotificationActive";
	final static String TEST_E_ADDRESS = "testEmailAddress";
	final static String EMAIL_NOTE_ADD = "emailNotificationAddress";
	final static String GREETINGS = "greetings";
	final static String GROUPLIST = "groupList";
	final static String GROUPS = "groups";
	final static String LANGUAGE = "language";
	final static String ENGLISH = "English";
	final static String PRIME_DID = "primaryDid";
	final static String TEST_DID = "testDiD";
	final static String SCREEN_BEHAVE = "screenBehavior";
	final static String SHOW_TRANS = "showTranscripts";
	final static String SMS_NOTE = "smsNotifications";
	final static String SMS_EMAIL_ACTIVE = "smsToEmailActive";
	final static String SMS_EMAIL_SUB = "smsToEmailSubject";
	final static String SPAM = "spam";
	final static String TIME_ZONE = "timezone";
	final static String TEST_T_ZONE = "testTimeZone";
	final static String USE_DID_CID = "useDidAsCallerId";
	final static String USE_DID_SOURCE = "useDidAsSource";

	final JsonArray emptyJsonArray = new JsonArray();
	JsonArray testGroupList = new JsonArray();
	JsonArray testGroupArray = new JsonArray();

	final JsonObject emptyJsonObject = new JsonObject();
	final static JsonObject TEST_NULL_OBJECT = null;
	JsonObject testJSONGroupOb = new JsonObject();
	JsonObject testJSONGroupOb1 = new JsonObject();
	JsonObject jsonPreSetting = new JsonObject();
	JsonObject jsonSetting = new JsonObject();
	JsonObject testJsonObject = new JsonObject();

	final DisabledId testDisabledID = new DisabledId("3", true);
	final DisabledId testDisabledID1 = new DisabledId("4", true);

	DisabledId[] testDisArray = { testDisabledID, testDisabledID1 };

	@Before
	public void setUp() throws Exception {
		Gson gson = new Gson();

		// Build groups
		testJSONGroupOb.addProperty("id", "testID");
		testJSONGroupOb.addProperty("name", "testName");
		testJSONGroupOb.addProperty("isCustomForwarding", false);
		testJSONGroupOb.addProperty("isCustomGreeting", false);
		testJSONGroupOb.addProperty("isCustomDirectConnect", false);
		testJSONGroupOb.addProperty(DIRECT_CONNECT, false);
		testJSONGroupOb.addProperty("greetingId", 1);
		testJSONGroupOb.add("disabledForwardingIds", emptyJsonObject);
		testGroup = new Group(testJSONGroupOb);
		// Insert testGroup into an array
		testGroupArray.add(gson.toJsonTree(testGroup).getAsJsonObject());
		// And move it into a new Object
		testJSONGroupOb1.add("testID", testJSONGroupOb);

		testGroupList.add(new JsonPrimitive("testID"));
		testGroupList.add(new JsonPrimitive("testID1"));

		jsonPreSetting.add(A_F_IDS, emptyJsonArray);
		jsonPreSetting.addProperty(BASE_URL, TEST_URL);
		jsonPreSetting.addProperty(CREDITS, 1);
		jsonPreSetting.addProperty(DFID, 1);
		jsonPreSetting.add(DID_INFOS, emptyJsonArray);
		jsonPreSetting.addProperty(DIRECT_CONNECT, true);
		jsonPreSetting.add(DID_MAP, emptyJsonObject);
		jsonPreSetting.addProperty(D_N_D, false);
		jsonPreSetting.add(EMAIL_ADD, emptyJsonObject);
		jsonPreSetting.addProperty(EMAIL_NOTE_ACTIVE, true);
		jsonPreSetting.addProperty(EMAIL_NOTE_ADD, TEST_E_ADDRESS);
		jsonPreSetting.add(GREETINGS, emptyJsonObject);
		jsonPreSetting.add(GROUPLIST, testGroupList);
		jsonPreSetting.add(GROUPS, testJSONGroupOb1);
		jsonPreSetting.addProperty(LANGUAGE, ENGLISH);
		jsonPreSetting.addProperty(PRIME_DID, TEST_DID);
		jsonPreSetting.addProperty(SCREEN_BEHAVE, 1);
		jsonPreSetting.addProperty(SHOW_TRANS, false);
		jsonPreSetting.add(SMS_NOTE, emptyJsonArray);
		jsonPreSetting.addProperty(SMS_EMAIL_ACTIVE, true);
		jsonPreSetting.addProperty(SMS_EMAIL_SUB, false);
		jsonPreSetting.addProperty(SPAM, SPAM);
		jsonPreSetting.addProperty(TIME_ZONE, TEST_T_ZONE);
		jsonPreSetting.addProperty(USE_DID_CID, true);
		jsonPreSetting.addProperty(USE_DID_SOURCE, false);
		testSetting = new Setting(jsonPreSetting);
		testSetting.setmDisabledIdList(null);

	}

	@Test
	public void testSettingNullObject() {

		testSetting = new Setting(testJsonObject);

		assertNull(testSetting.getmActiveForwardingList());

	}

	@Test
	public void testSettings() {

		assertEquals(1, testSetting.getCredits());

	}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#toJsonObject()}.
	 */
	@Ignore
	@Test
	public void testToJsonObjectNullEmailAndDiD() {

		jsonPreSetting.add(A_F_IDS, emptyJsonArray);
		jsonPreSetting.addProperty(BASE_URL, TEST_URL);
		jsonPreSetting.addProperty(CREDITS, 1);
		jsonPreSetting.addProperty(DFID, 1);
		jsonPreSetting.add(DID_INFOS, emptyJsonArray);
		jsonPreSetting.addProperty(DIRECT_CONNECT, true);
		jsonPreSetting.add(DID_MAP, emptyJsonObject);
		jsonPreSetting.addProperty(D_N_D, false);
		jsonPreSetting.add(EMAIL_ADD, TEST_NULL_OBJECT);
		jsonPreSetting.addProperty(EMAIL_NOTE_ACTIVE, true);
		jsonPreSetting.addProperty(EMAIL_NOTE_ADD, TEST_E_ADDRESS);
		jsonPreSetting.add(GREETINGS, emptyJsonObject);
		jsonPreSetting.add(GROUPLIST, testGroupList);
		jsonPreSetting.add(GROUPS, testJSONGroupOb1);
		jsonPreSetting.addProperty(LANGUAGE, ENGLISH);
		jsonPreSetting.addProperty(PRIME_DID, TEST_DID);
		jsonPreSetting.addProperty(SCREEN_BEHAVE, 1);
		jsonPreSetting.addProperty(SHOW_TRANS, false);
		jsonPreSetting.add(SMS_NOTE, emptyJsonArray);
		jsonPreSetting.addProperty(SMS_EMAIL_ACTIVE, true);
		jsonPreSetting.addProperty(SMS_EMAIL_SUB, false);
		jsonPreSetting.addProperty(SPAM, SPAM);
		jsonPreSetting.addProperty(TIME_ZONE, TEST_T_ZONE);
		jsonPreSetting.addProperty(USE_DID_CID, true);
		jsonPreSetting.addProperty(USE_DID_SOURCE, false);

		testSetting = new Setting(jsonPreSetting);
		testSetting.setmDisabledIdList(null);

		testJsonObject = testSetting.toJsonObject();

		resultString = "{\"primaryDid\":\"testDiD\",\"defaultGreetingId\":1,\"useDidAsSource\":false,\"doNotDisturb\":false,\"smsNotifications\":[],\"emailNotificationActive\":true,\"credits\":1,\"activeForwardingIds\":[],\"emailNotificationAddress\":\"testEmailAddress\",\"smsToEmailSubject\":false,\"baseUrl\":\"testURL\",\"timezone\":\"testTimeZone\",\"groupList\":[\"testID\",\"testID1\"],\"didInfos\":[],\"greetings\":[],\"showTranscripts\":false,\"directConnect\":true,\"screenBehavior\":1,\"language\":\"English\",\"spam\":\"spam\",\"useDidAsCallerId\":true,\"smsToEmailActive\":true,\"groups\":{\"testID\":{\"id\":\"testID\",\"greetingId\":1,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{},\"name\":\"testName\",\"isCustomDirectConnect\":false,\"directConnect\":false}}}";

		assertEquals(resultString, testJsonObject.toString());

		}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#toJsonObject()}.
	 */
	@Ignore
	@Test
	public void testToJsonObject() {

		final JsonArray testEmailArray = new JsonArray();
		testEmailArray.add(new JsonPrimitive("test@email.com"));

		final JsonObject testDiDObject = new JsonObject();
		testDiDObject.addProperty("1", true);

		jsonPreSetting.add(A_F_IDS, emptyJsonArray);
		jsonPreSetting.addProperty(BASE_URL, TEST_URL);
		jsonPreSetting.addProperty(CREDITS, 1);
		jsonPreSetting.addProperty(DFID, 1);
		jsonPreSetting.add(DID_INFOS, emptyJsonArray);
		jsonPreSetting.addProperty(DIRECT_CONNECT, true);
		jsonPreSetting.add(DID_MAP, testDiDObject);
		jsonPreSetting.addProperty(D_N_D, false);
		jsonPreSetting.add(EMAIL_ADD, testEmailArray);
		jsonPreSetting.addProperty(EMAIL_NOTE_ACTIVE, true);
		jsonPreSetting.addProperty(EMAIL_NOTE_ADD, TEST_E_ADDRESS);
		jsonPreSetting.add(GREETINGS, emptyJsonObject);
		jsonPreSetting.add(GROUPLIST, testGroupList);
		jsonPreSetting.add(GROUPS, testJSONGroupOb1);
		jsonPreSetting.addProperty(LANGUAGE, ENGLISH);
		jsonPreSetting.addProperty(PRIME_DID, TEST_DID);
		jsonPreSetting.addProperty(SCREEN_BEHAVE, 1);
		jsonPreSetting.addProperty(SHOW_TRANS, false);
		jsonPreSetting.add(SMS_NOTE, emptyJsonArray);
		jsonPreSetting.addProperty(SMS_EMAIL_ACTIVE, true);
		jsonPreSetting.addProperty(SMS_EMAIL_SUB, false);
		jsonPreSetting.addProperty(SPAM, SPAM);
		jsonPreSetting.addProperty(TIME_ZONE, TEST_T_ZONE);
		jsonPreSetting.addProperty(USE_DID_CID, true);
		jsonPreSetting.addProperty(USE_DID_SOURCE, false);

		testSetting = new Setting(jsonPreSetting);

		testJsonObject = testSetting.toJsonObject().getAsJsonObject();

		resultString = "{\"doNotDisturb\":false,\"disabledIdMap\":{\"1\":true},\"smsNotifications\":[],\"credits\":1,\"baseUrl\":\"testURL\",\"groupList\":[\"testID\",\"testID1\"],\"timezone\":\"testTimeZone\",\"greetings\":[],\"showTranscripts\":false,\"useDidAsCallerId\":true,\"groups\":{\"testID\":{\"id\":\"testID\",\"greetingId\":1,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{},\"name\":\"testName\",\"isCustomDirectConnect\":false,\"directConnect\":false}},\"primaryDid\":\"testDiD\",\"defaultGreetingId\":1,\"useDidAsSource\":false,\"emailNotificationActive\":true,\"emailNotificationAddress\":\"testEmailAddress\",\"activeForwardingIds\":[],\"smsToEmailSubject\":false,\"didInfos\":[],\"directConnect\":true,\"screenBehavior\":1,\"language\":\"English\",\"spam\":\"spam\",\"smsToEmailActive\":true,\"emailAddresses\":\"test@email.com\"}";

		assertEquals(resultString, testJsonObject.toString());

		}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#getGroupListAsList()}
	 * .
	 */
	@Test
	public void testGetGroupListAsList() {

		final JsonArray testEmailArray = new JsonArray();
		testEmailArray.add(new JsonPrimitive("test@email.com"));

		final JsonObject testDiDObject = new JsonObject();
		testDiDObject.addProperty("1", true);

		jsonPreSetting.add(A_F_IDS, emptyJsonArray);
		jsonPreSetting.addProperty(BASE_URL, TEST_URL);
		jsonPreSetting.addProperty(CREDITS, 1);
		jsonPreSetting.addProperty(DFID, 1);
		jsonPreSetting.add(DID_INFOS, emptyJsonArray);
		jsonPreSetting.addProperty(DIRECT_CONNECT, true);
		jsonPreSetting.add(DID_MAP, testDiDObject);
		jsonPreSetting.addProperty(D_N_D, false);
		jsonPreSetting.add(EMAIL_ADD, testEmailArray);
		jsonPreSetting.addProperty(EMAIL_NOTE_ACTIVE, true);
		jsonPreSetting.addProperty(EMAIL_NOTE_ADD, TEST_E_ADDRESS);
		jsonPreSetting.add(GREETINGS, emptyJsonObject);
		jsonPreSetting.add(GROUPLIST, testGroupList);
		jsonPreSetting.add(GROUPS, testJSONGroupOb1);
		jsonPreSetting.addProperty(LANGUAGE, ENGLISH);
		jsonPreSetting.addProperty(PRIME_DID, TEST_DID);
		jsonPreSetting.addProperty(SCREEN_BEHAVE, 1);
		jsonPreSetting.addProperty(SHOW_TRANS, false);
		jsonPreSetting.add(SMS_NOTE, emptyJsonArray);
		jsonPreSetting.addProperty(SMS_EMAIL_ACTIVE, true);
		jsonPreSetting.addProperty(SMS_EMAIL_SUB, false);
		jsonPreSetting.addProperty(SPAM, SPAM);
		jsonPreSetting.addProperty(TIME_ZONE, TEST_T_ZONE);
		jsonPreSetting.addProperty(USE_DID_CID, true);
		jsonPreSetting.addProperty(USE_DID_SOURCE, false);

		testSetting = new Setting(jsonPreSetting);

		resultString = "[testID, testID1]";

		assertEquals(resultString, testSetting.getGroupListAsList().toString());
	}

}
