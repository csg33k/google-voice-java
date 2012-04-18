package test.datatypes;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;
import com.techventus.server.voice.datatypes.Group;

/**
 * 
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class GroupTest {
	// testGroups
	Group testGroup;
	Group testGroup1;
	Group testGroup2;
	Group testGroup3;
	
	String testString;

	// DisabledForwardingIDs
	final DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId(
			"2", true);
	final DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId(
			"3", true);

	List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
	List<DisabledForwardingId> testList1 = new ArrayList<DisabledForwardingId>();
	List<Group> resultList = new ArrayList<Group>();
	List<Group> testGrouplist = new ArrayList<Group>();

	JSONArray testJSArray1 = new JSONArray();
	JSONObject disabledFIDs = new JSONObject();
	JSONObject testJSONOb1 = new JSONObject();
	JSONObject testJSONOb3 = new JSONObject();
	JSONObject testGroupObject = new JSONObject();

	@Before
	public void setUp() throws Exception {

		// Populate DFID List1
		testList1.add(testDisabledForwardingId1);
		testList1.add(testDisabledForwardingId);

		// JSON Array to attempt to mirror populated list.
		testJSArray1.put(3);
		testJSArray1.put(2);

		// New JSON objects to mirror lists above.
		JSONObject disabledFIDs1 = testJSArray1.toJSONObject(testJSArray1);

		// Construct Object 1
		testJSONOb1.put("id", "testID");
		testJSONOb1.put("name", "testName");
		testJSONOb1.put("isCustomForwarding", false);
		testJSONOb1.put("isCustomGreeting", false);
		testJSONOb1.put("isCustomDirectConnect", false);
		testJSONOb1.put("directConnect", false);
		testJSONOb1.put("greetingId", 0);
		testJSONOb1.put("disabledForwardingIds", disabledFIDs);
		
		// Construct Object 3
		testJSONOb3.put("id", "testID1");
		testJSONOb3.put("name", "testName1");
		testJSONOb3.put("isCustomForwarding", false);
		testJSONOb3.put("isCustomGreeting", false);
		testJSONOb3.put("isCustomDirectConnect", false);
		testJSONOb3.put("directConnect", false);
		testJSONOb3.put("greetingId", 0);
		testJSONOb3.put("disabledForwardingIds", disabledFIDs1);

		// Instantiate testGroups
		testGroup = new Group("testID", "testName", false, testList, false,
				false, false, 0);
		testGroup1 = new Group(testJSONOb1);
		testGroup2 = new Group("testID1", "testName1", false, testList1, false,
				false, false, 0);
		testGroup3 = new Group(testJSONOb3);

	}

	@Test
	public void testJSONNullGroupObject() throws JSONException {

		
		Group nullGroup = new Group(testGroupObject);
		
		String resultString = "{id=null;name=null;isCustomDirectConnect=false;directConnect=false;isCustomGreeting=false;isCustomForwarding=false;greetingId=0;disabledForwardingIds=null}";

		Assert.assertEquals(resultString, nullGroup.toString());
		
	}

	@Test
	public void testJSONGroupObjectEqualsJavaGroupObject() {

		final boolean test = testGroup.toString().equals(testGroup1.toString());

		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testCreateGroupSettingsFromJsonResponse() {
		
		resultList.add(testGroup);
		
		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"disabledForwardingIds\":{1},\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";
		
		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);
		
		Assert.assertEquals(resultList.toString(), testGrouplist.toString());
	}
	
	@Test
	public void testCreateGroupSettingsFromJsonResponseNullDisabledForwardingID() {
		
		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";
		
		try {
		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);
		}
		catch(Exception e) {
			
		}
		Assert.assertEquals(resultList.toString(), testGrouplist.toString());
	}
	
	@Test
	public void testCreateGroupSettingsFromJsonResponseEmptyDisabledForwardingID() {
		
		resultList.add(testGroup);
		
		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"disabledForwardingIds\":{},\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";
		
		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);
		
		Assert.assertEquals(resultList.toString(), testGrouplist.toString());
	}
	
	@Test
	public void testListToJson() {
		
		testGrouplist.add(testGroup1);
		testGrouplist.add(testGroup3);
		
		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"greetingId\":0,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{},\"name\":\"testName\",\"isCustomDirectConnect\":false,\"directConnect\":false},\"testID1\":{\"id\":\"testID1\",\"greetingId\":0,\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"disabledForwardingIds\":{\"3\":true,\"2\":true},\"name\":\"testName1\",\"isCustomDirectConnect\":false,\"directConnect\":false}}";
		
		Assert.assertEquals(testString, Group.listToJson(testGrouplist).toString());
		
	}
	
	@Test
	public void testCreateArrayFromJsonObject() throws JSONException {
		
		testGroupObject.put("testID",testJSONOb1);
		
		Group[] resultGroupArray = {testGroup};
		Group[] testGroupArray = Group.createArrayFromJsonObject(testGroupObject);
		
		Assert.assertEquals(resultGroupArray[0].toString(), testGroupArray[0].toString());
		
	}
	

	@Test
	public void testIsPhoneDisabledFalseEmptyList() {

		final boolean test = testGroup.isPhoneDisabled(1);
		final boolean test1 = testGroup1.isPhoneDisabled(1);

		Assert.assertEquals(false, test);
		Assert.assertEquals(false, test1);
	}

	@Test
	public void testIsPhoneDisabledFalseNotInList() throws JSONException {

		final boolean test = testGroup2.isPhoneDisabled(1);
		final boolean test1 = testGroup3.isPhoneDisabled(1);

		Assert.assertEquals(false, test);
		Assert.assertEquals(false, test1);
	}

	@Test
	public void testIsPhoneDisabledTrue() throws JSONException {

		final boolean test = testGroup2.isPhoneDisabled(2);
		final boolean test1 = testGroup3.isPhoneDisabled(2);

		Assert.assertEquals(true, test);
		Assert.assertEquals(true, test1);
	}

}
