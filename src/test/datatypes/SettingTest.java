/**
 * 
 */
package test.datatypes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;
import com.techventus.server.voice.datatypes.DisabledId;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Setting;

/**
 * @author Brett Futral @ Catalyst IT Services
 * 
 */
public class SettingTest {

	Setting testSetting;
	Setting testSetting1;
	Setting testSetting2;
	Group testGroup;
	String resultString;

	final JSONArray emptyJSONArray = new JSONArray();
	JSONArray testGroupList = new JSONArray();
	JSONArray testGroupArray = new JSONArray();
	

	final JSONObject emptyJSONObject = new JSONObject();
	JSONObject testJSONGroupOb = new JSONObject();
	JSONObject testJSONGroupOb1 = new JSONObject();
	JSONObject jsonPreSetting = new JSONObject();
	JSONObject jsonSetting = new JSONObject();
	JSONObject testJSONObject = new JSONObject();

	final DisabledId testDisabledID = new DisabledId("3", true);
	final DisabledId testDisabledID1 = new DisabledId("4", true);

	DisabledId[] testDisArray = { testDisabledID, testDisabledID1 };

	@Before
	public void setUp() throws Exception {

		// Build groups
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
		testGroupArray.put(testGroup);
		// And move it into a new Object
		testJSONGroupOb1.put("testID", testJSONGroupOb);
		
		testGroupList.put("testID");
		testGroupList.put("testID1");
		
		jsonPreSetting.put("activeForwardingIds", emptyJSONArray);
		jsonPreSetting.put("baseUrl", "testURL");
		jsonPreSetting.put("credits", 1);
		jsonPreSetting.put("defaultGreetingId", 1);
		jsonPreSetting.put("didInfos", emptyJSONArray);
		jsonPreSetting.put("directConnect", true);
		jsonPreSetting.put("disabledIdMap", emptyJSONObject);
		jsonPreSetting.put("doNotDisturb", false);
		jsonPreSetting.put("emailAddresses", emptyJSONObject);
		jsonPreSetting.put("emailNotificationActive", true);
		jsonPreSetting.put("emailNotificationAddress", "testEmailAddress");
		jsonPreSetting.put("greetings", emptyJSONObject);
		jsonPreSetting.put("groupList", testGroupList);
		jsonPreSetting.put("groups", testJSONGroupOb1);
		jsonPreSetting.put("language", "English");
		jsonPreSetting.put("primaryDid", "testDiD");
		jsonPreSetting.put("screenBehavior", 1);
		jsonPreSetting.put("showTranscripts", false);
		jsonPreSetting.put("smsNotifications", emptyJSONArray);
		jsonPreSetting.put("smsToEmailActive", true);
		jsonPreSetting.put("smsToEmailSubject", false);
		jsonPreSetting.put("spam", "spam");
		jsonPreSetting.put("timezone", "testTimeZone");
		jsonPreSetting.put("useDidAsCallerId", true);
		jsonPreSetting.put("useDidAsSource", false);
		testSetting = new Setting(jsonPreSetting);
		testSetting.setmDisabledIdList(null);

	}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#Setting(gvjava.org.json.JSONObject)}
	 * .
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testSettingNullObject() throws JSONException {

		testSetting = new Setting(testJSONObject);

		Assert.assertNull(testSetting.getmActiveForwardingList());

	}
	
	@Test
	public void testSetting() throws JSONException {

		Assert.assertEquals(1,testSetting.getCredits());

	}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#toJsonObject()}.
	 * @throws JSONException 
	 */
	@Test
	public void testToJsonObjectNullEmailAndDiD() throws JSONException {
		
		JSONObject testNullJSONObject = null;

		jsonPreSetting.put("activeForwardingIds", emptyJSONArray);
		jsonPreSetting.put("baseUrl", "testURL");
		jsonPreSetting.put("credits", 1);
		jsonPreSetting.put("defaultGreetingId", 1);
		jsonPreSetting.put("didInfos", emptyJSONArray);
		jsonPreSetting.put("directConnect", true);
		jsonPreSetting.put("disabledIdMap", emptyJSONObject);
		jsonPreSetting.put("doNotDisturb", false);
		jsonPreSetting.put("emailAddresses", testNullJSONObject);
		jsonPreSetting.put("emailNotificationActive", true);
		jsonPreSetting.put("emailNotificationAddress", "testEmailAddress");
		jsonPreSetting.put("greetings", emptyJSONObject);
		jsonPreSetting.put("groupList", testGroupList);
		jsonPreSetting.put("groups", testJSONGroupOb1);
		jsonPreSetting.put("language", "English");
		jsonPreSetting.put("primaryDid", "testDiD");
		jsonPreSetting.put("screenBehavior", 1);
		jsonPreSetting.put("showTranscripts", false);
		jsonPreSetting.put("smsNotifications", emptyJSONArray);
		jsonPreSetting.put("smsToEmailActive", true);
		jsonPreSetting.put("smsToEmailSubject", false);
		jsonPreSetting.put("spam", "spam");
		jsonPreSetting.put("timezone", "testTimeZone");
		jsonPreSetting.put("useDidAsCallerId", true);
		jsonPreSetting.put("useDidAsSource", false);
		
		testSetting = new Setting(jsonPreSetting);
		testSetting.setmDisabledIdList(null);
		
		testJSONObject = testSetting.toJsonObject();
		
		resultString = "{\"primaryDid\":\"testDiD\",\"defaultGreetingId\":1,\"useDidAsSource\":false,\"doNotDisturb\":false,\"smsNotifications\":[],\"emailNotificationActive\":true,\"credits\":1,\"activeForwardingIds\":[],\"emailNotificationAddress\":\"testEmailAddress\",\"smsToEmailSubject\":false,\"baseUrl\":\"testURL\",\"timezone\":\"testTimeZone\",\"groupList\":[\"testID\",\"testID1\"],\"didInfos\":[],\"greetings\":[],\"showTranscripts\":false,\"directConnect\":true,\"screenBehavior\":1,\"language\":\"English\",\"spam\":\"spam\",\"useDidAsCallerId\":true,\"smsToEmailActive\":true,\"groups\":{\"testID\":{\"id\":\"testID\",\"greetingId\":1,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{},\"name\":\"testName\",\"isCustomDirectConnect\":false,\"directConnect\":false}}}";

		Assert.assertEquals(resultString, testJSONObject.toString());
		
		}
	
	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#toJsonObject()}.
	 * @throws JSONException 
	 */
	@Test
	public void testToJsonObject() throws JSONException {
		
		JSONArray testEmailArray = new JSONArray();
		testEmailArray.put("test@email.com");
		
		JSONObject testDiDObject = new JSONObject();
		testDiDObject.put("1", true);

		jsonPreSetting.put("activeForwardingIds", emptyJSONArray);
		jsonPreSetting.put("baseUrl", "testURL");
		jsonPreSetting.put("credits", 1);
		jsonPreSetting.put("defaultGreetingId", 1);
		jsonPreSetting.put("didInfos", emptyJSONArray);
		jsonPreSetting.put("directConnect", true);
		jsonPreSetting.put("disabledIdMap", testDiDObject);
		jsonPreSetting.put("doNotDisturb", false);
		jsonPreSetting.put("emailAddresses", testEmailArray);
		jsonPreSetting.put("emailNotificationActive", true);
		jsonPreSetting.put("emailNotificationAddress", "testEmailAddress");
		jsonPreSetting.put("greetings", emptyJSONObject);
		jsonPreSetting.put("groupList", testGroupList);
		jsonPreSetting.put("groups", testJSONGroupOb1);
		jsonPreSetting.put("language", "English");
		jsonPreSetting.put("primaryDid", "testDiD");
		jsonPreSetting.put("screenBehavior", 1);
		jsonPreSetting.put("showTranscripts", false);
		jsonPreSetting.put("smsNotifications", emptyJSONArray);
		jsonPreSetting.put("smsToEmailActive", true);
		jsonPreSetting.put("smsToEmailSubject", false);
		jsonPreSetting.put("spam", "spam");
		jsonPreSetting.put("timezone", "testTimeZone");
		jsonPreSetting.put("useDidAsCallerId", true);
		jsonPreSetting.put("useDidAsSource", false);
		
		testSetting = new Setting(jsonPreSetting);
		
		testJSONObject = testSetting.toJsonObject();
		
		resultString = "{\"doNotDisturb\":false,\"disabledIdMap\":{\"1\":true},\"smsNotifications\":[],\"credits\":1,\"baseUrl\":\"testURL\",\"groupList\":[\"testID\",\"testID1\"],\"timezone\":\"testTimeZone\",\"greetings\":[],\"showTranscripts\":false,\"useDidAsCallerId\":true,\"groups\":{\"testID\":{\"id\":\"testID\",\"greetingId\":1,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{},\"name\":\"testName\",\"isCustomDirectConnect\":false,\"directConnect\":false}},\"primaryDid\":\"testDiD\",\"defaultGreetingId\":1,\"useDidAsSource\":false,\"emailNotificationActive\":true,\"emailNotificationAddress\":\"testEmailAddress\",\"activeForwardingIds\":[],\"smsToEmailSubject\":false,\"didInfos\":[],\"directConnect\":true,\"screenBehavior\":1,\"language\":\"English\",\"spam\":\"spam\",\"smsToEmailActive\":true,\"emailAddresses\":\"test@email.com\"}";

		Assert.assertEquals(resultString, testJSONObject.toString());
		
		}

	/**
	 * Test method for
	 * {@link com.techventus.server.voice.datatypes.Setting#getGroupListAsList()}
	 * .
	 * @throws JSONException 
	 */
	@Test
	public void testGetGroupListAsList() throws JSONException {
		
		JSONArray testEmailArray = new JSONArray();
		testEmailArray.put("test@email.com");
		
		JSONObject testDiDObject = new JSONObject();
		testDiDObject.put("1", true);

		jsonPreSetting.put("activeForwardingIds", emptyJSONArray);
		jsonPreSetting.put("baseUrl", "testURL");
		jsonPreSetting.put("credits", 1);
		jsonPreSetting.put("defaultGreetingId", 1);
		jsonPreSetting.put("didInfos", emptyJSONArray);
		jsonPreSetting.put("directConnect", true);
		jsonPreSetting.put("disabledIdMap", testDiDObject);
		jsonPreSetting.put("doNotDisturb", false);
		jsonPreSetting.put("emailAddresses", testEmailArray);
		jsonPreSetting.put("emailNotificationActive", true);
		jsonPreSetting.put("emailNotificationAddress", "testEmailAddress");
		jsonPreSetting.put("greetings", emptyJSONObject);
		jsonPreSetting.put("groupList", testGroupList);
		jsonPreSetting.put("groups", testJSONGroupOb1);
		jsonPreSetting.put("language", "English");
		jsonPreSetting.put("primaryDid", "testDiD");
		jsonPreSetting.put("screenBehavior", 1);
		jsonPreSetting.put("showTranscripts", false);
		jsonPreSetting.put("smsNotifications", emptyJSONArray);
		jsonPreSetting.put("smsToEmailActive", true);
		jsonPreSetting.put("smsToEmailSubject", false);
		jsonPreSetting.put("spam", "spam");
		jsonPreSetting.put("timezone", "testTimeZone");
		jsonPreSetting.put("useDidAsCallerId", true);
		jsonPreSetting.put("useDidAsSource", false);
		
		testSetting = new Setting(jsonPreSetting);
		
		resultString = "[testID, testID1]";
		
		Assert.assertEquals(resultString, testSetting.getGroupListAsList().toString());
	}

}
