package test.datatypes;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.techventus.server.voice.util.ParsingUtil;
import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;
import com.techventus.server.voice.datatypes.Group;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class GroupTest {

//	// testGroups
	Group testGroup;
	Group testGroup1;
	Group testGroup2;
	Group testGroup3;

	String testString;
	String resultString;
	private final static String TEST_ID = "testID";

	// DisabledForwardingIDs
	final DisabledForwardingId testDFId = new DisabledForwardingId(
			"2", true);
	final DisabledForwardingId testDFId1 = new DisabledForwardingId(
			"3", true);

	List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
	List<DisabledForwardingId> testList1 = new ArrayList<DisabledForwardingId>();
	List<Group> resultList = new ArrayList<Group>();
	List<Group> testGrouplist = new ArrayList<Group>();

	JsonArray testJSArray1 = new JsonArray();
	JsonObject disabledFIDs = new JsonObject();
	JsonObject testJSONOb1 = new JsonObject();
	JsonObject testJSONOb3 = new JsonObject();
	JsonObject testGroupObject = new JsonObject();

	@Before
	public void setUp() throws Exception {

		// Populate DFID List1
		testList1.add(testDFId1);
		testList1.add(testDFId);

		// JSON Array to attempt to mirror populated list.
		testJSArray1.add(new JsonPrimitive(3));
		testJSArray1.add(new JsonPrimitive(2));

		// New JSON objects to mirror lists above.
		final JsonArray disabledFIDs1 = testJSArray1;
//		ParsingUtil.jsonIntArrayToIntArray()

		// Construct Object 1
		testJSONOb1.addProperty("id", TEST_ID);
		testJSONOb1.addProperty("name", "testName");
		testJSONOb1.addProperty("isCustomForwarding", false);
		testJSONOb1.addProperty("isCustomGreeting", false);
		testJSONOb1.addProperty("isCustomDirectConnect", false);
		testJSONOb1.addProperty("directConnect", false);
		testJSONOb1.addProperty("greetingId", 0);
		testJSONOb1.add("disabledForwardingIds", disabledFIDs);

		// Construct Object 3
		testJSONOb3.addProperty("id", "testID1");
		testJSONOb3.addProperty("name", "testName1");
		testJSONOb3.addProperty("isCustomForwarding", false);
		testJSONOb3.addProperty("isCustomGreeting", false);
		testJSONOb3.addProperty("isCustomDirectConnect", false);
		testJSONOb3.addProperty("directConnect", false);
		testJSONOb3.addProperty("greetingId", 0);
		testJSONOb3.add("disabledForwardingIds", disabledFIDs1);

		// Instantiate testGroups
		testGroup = new Group(TEST_ID, "testName", false, testList, false,
				false, false, 0);
		testGroup1 = new Group(testJSONOb1);
		testGroup2 = new Group("testID1", "testName1", false, testList1, false,
				false, false, 0);
		testGroup3 = new Group(testJSONOb3);

	}

	@Test
	public void testJSONNullGroupObject() {


		final Group nullGroup = new Group(testGroupObject);

		resultString = "{id=null;name=null;isCustomDirectConnect=false;directConnect=false;isCustomGreeting=false;isCustomForwarding=false;greetingId=0;disabledForwardingIds=null}";

		assertEquals(resultString, nullGroup.toString());

	}

	@Test
	public void testJSONGroupObjectEqualsJavaGroupObject() {

		final boolean test = testGroup.toString().equals(testGroup1.toString());

		assertEquals(true, test);
	}

	@Test
	public void testCreateGroupSettingsFromJsonResponse() {

		resultList.add(testGroup);

		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"disabledForwardingIds\":{1},\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";

		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);

		assertEquals(resultList.toString(), testGrouplist.toString());
	}

	@Test
	public void testCreateGroupSettingsFromJsonResponseNullDisabledForwardingID() {

		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";

		try {
		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);
		}
		catch(Exception e) {
			testGrouplist = resultList;
		}
		assertEquals(resultList, testGrouplist);
	}

	@Test
	public void testCreateGroupSettingsFromJsonResponseEmptyDisabledForwardingID() {

		resultList.add(testGroup);

		testString = "\"groups\":{\"testID\":{\"id\":\"testID\",\"name\":\"testName\",\"disabledForwardingIds\":{},\"isCustomForwarding\":false,\"isCustomGreeting\":false,\"isCustomDirectConnect\":false,\"directConnect\":false,\"greetingId\":0,\"isCircle\":true,\"isCustomTranscriptionLanguage\":false,\"transcriptionLanguage\":\"\"},\"groupList\"";

		testGrouplist = Group.createGroupSettingsFromJsonResponse(testString);

		assertEquals(resultList.toString(), testGrouplist.toString());
	}

	@Test
	public void testCreateArrayFromJsonObject() {

		testGroupObject.add(TEST_ID,testJSONOb1);

		final Group[] resultGroupArray = {testGroup};
		final Group[] testGroupArray = Group.createArrayFromJsonObject(testGroupObject);

		assertEquals(resultGroupArray[0].toString(), testGroupArray[0].toString());

	}

//	@Test
//	public void testCreateJsonObjectArrayFromJsonObject() {
//
//		resultString = "{\"testID\":\"{id=testID;name=testName;isCustomDirectConnect=false;directConnect=false;isCustomGreeting=false;isCustomForwarding=false;greetingId=0;disabledForwardingIds=[]}\"}";
//
//		testGroupObject.add("obj1", testJSONOb1);
//
//		final JsonObject[] testObjectArray = Group.createJsonObjectArrayFromJsonObject(testGroupObject);
//
//		assertEquals(resultString, testObjectArray[0].toString());
//	}

//	@Test
//	public void testCreateJsonObjectFromJsonObject() throws JSONException {
//
//		resultString = "{\"testID\":\"{id=testID;name=testName;isCustomDirectConnect=false;directConnect=false;isCustomGreeting=false;isCustomForwarding=false;greetingId=0;disabledForwardingIds=[]}\"}";
//
//		testGroupObject.put("obj1", testJSONOb1);
//
//		final JsonObject testObject = Group.createJsonObjectFromJsonObject(testGroupObject);
//
//		assertEquals(resultString, testObject.toString());
//
//	}

	@Test
	public void testIsPhoneDisabledFalseNullList() {



		final JsonObject testJSONOb4 = new JsonObject();
		testJSONOb4.addProperty("id", TEST_ID);
		testJSONOb4.addProperty("name", "testName");
		testJSONOb4.addProperty("isCustomForwarding", false);
		testJSONOb4.addProperty("isCustomGreeting", false);
		testJSONOb4.addProperty("isCustomDirectConnect", false);
		testJSONOb4.addProperty("directConnect", false);
		testJSONOb4.addProperty("greetingId", 0);

		final Group testGroup4 = new Group(testJSONOb4);

		final boolean testNullList = testGroup4.isPhoneDisabled(1);

		assertEquals(false, testNullList);


	}

	@Test
	public void testIsPhoneDisabledFalseEmptyList() {

		final boolean test = testGroup.isPhoneDisabled(1);
		final boolean test1 = testGroup1.isPhoneDisabled(1);

		assertEquals(false, test);
		assertEquals(false, test1);
	}

	@Test
	public void testIsPhoneDisabledFalseNotInList() {

		final boolean test = testGroup2.isPhoneDisabled(1);
		final boolean test1 = testGroup3.isPhoneDisabled(1);

		assertEquals(false, test);
		assertEquals(false, test1);
	}

	@Test
	public void testIsPhoneDisabledTrue() {

		final boolean test = testGroup2.isPhoneDisabled(2);
		final boolean test1 = testGroup3.isPhoneDisabled(2);

		assertEquals(true, test);
		assertEquals(true, test1);
	}

}
