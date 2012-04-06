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

public class GroupTest {
	
	Group testGroup;
	Group testGroup1;
	Group testGroup2;
	Group testGroup3;
	
		
	@Before
	public void setUp() throws Exception {
		
		//DisabledForwardingIDs
		final DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId("2", true);
		final DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId("3", true);

		//Empty List
		List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
		
		//Populated List
		List<DisabledForwardingId> testList1 = new ArrayList<DisabledForwardingId>();
		testList1.add(testDisabledForwardingId1);
		testList1.add(testDisabledForwardingId);
		
		//JSON Array to attempt to mirror populated list.
		JSONArray testJSArray1 = new JSONArray();
		testJSArray1.put(3);
		testJSArray1.put(2);
		
		//Two new JSON objects to mirror lists above.
		JSONObject disabledFIDs = new JSONObject();
		JSONObject disabledFIDs1 = testJSArray1.toJSONObject(testJSArray1);
		
		//Two new JSON objects to construct groups with
		// Object 1
		JSONObject testJSONOb1 = new JSONObject();
		testJSONOb1.put("id", "testID");
		testJSONOb1.put("name", "testName");
		testJSONOb1.put("isCustomForwarding", false);
		testJSONOb1.put("isCustomGreeting", false);
		testJSONOb1.put("isCustomDirectConnect", false);
		testJSONOb1.put("directConnect", false);
		testJSONOb1.put("greetingId", 1);
		testJSONOb1.put("disabledForwardingIds", disabledFIDs);
		//Object 2
		JSONObject testJSONOb3 = new JSONObject();
		testJSONOb3.put("id", "testID");
		testJSONOb3.put("name", "testName");
		testJSONOb3.put("isCustomForwarding", false);
		testJSONOb3.put("isCustomGreeting", false);
		testJSONOb3.put("isCustomDirectConnect", false);
		testJSONOb3.put("directConnect", false);
		testJSONOb3.put("greetingId", 1);
		testJSONOb3.put("disabledForwardingIds", disabledFIDs1);
		
		//Test groups
		testGroup = new Group("testID", "testName", false, testList, false, false, false, 1);
		testGroup1 = new Group(testJSONOb1);
		testGroup2 = new Group("testID", "testName", false, testList1, false, false, false, 1);
		testGroup3 = new Group(testJSONOb3);
		
		

	}

	@Test
	public void testJSONGroupObject() throws JSONException {
		
		final boolean test = testGroup.toString().equals(testGroup1.toString());
		final boolean test1 = testGroup2.toString().equals(testGroup3.toString());
		
		Assert.assertEquals(true, test);
		Assert.assertEquals(true, test1);
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
