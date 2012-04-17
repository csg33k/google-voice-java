package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledId;

/**
 * 
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class DisabledIdTest {

	// Golden DisabledId Object
	final DisabledId goldDisabledID = new DisabledId("1", false);

	// Test Object
	DisabledId testDisabledID;
	JSONObject testDisabledIDJSON = new JSONObject();
	String testString;
	List<DisabledId> testList = new ArrayList<DisabledId>();

	@Test
	public void testDisabledIDJSONNullObjectSaveModeTrue()
			throws JSONException {

		JSONObject testDisabledIDJSON = new JSONObject();

		testDisabledID = new DisabledId(testDisabledIDJSON, true);

		Assert.assertNotNull(testDisabledID);
	}

	@Test
	public void testDisabledIDJSONNullObjectSaveModeFalse() {

		try {
			testDisabledID = new DisabledId(testDisabledIDJSON, false);
		} catch (Exception e) {

		}

		Assert.assertNull(testDisabledID);
	}

	@Test
	public void testDisabledIdJSONSaveModeFalse() throws JSONException {

		testDisabledIDJSON.put("id", "1");
		testDisabledIDJSON.put("disabled", false);

		testDisabledID = new DisabledId(testDisabledIDJSON, false);

		final boolean test = goldDisabledID.toString().equals(
				testDisabledID.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledIdJSONSaveModeTrue() throws JSONException {

		testDisabledIDJSON.put("id", "1");
		testDisabledIDJSON.put("disabled", false);

		testDisabledID = new DisabledId(testDisabledIDJSON, true);

		final boolean test = goldDisabledID.toString().equals(
				testDisabledID.toString());

		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testCreateDisabledIdListFromJsonPartResponseEmptyString() {
		
		testString = "";
		
		Assert.assertEquals(testList, DisabledId.createDisabledIdListFromJsonPartResponse(testString));
		
	}
	
	@Test
	public void testCreateDisabledIdListFromJsonPartResponseNullString() {
		
		testString = null;
		
		List<DisabledId> testListWithNullString = new ArrayList<DisabledId>();
		
		try {
			testListWithNullString = DisabledId.createDisabledIdListFromJsonPartResponse(testString);
		}
		catch(Exception e) {
			
		}
		
		Assert.assertEquals(testList, testListWithNullString);
		
	}
	
	@Test
	public void testCreateDisabledIdListFromJsonPartResponse() throws JSONException {
		
		JSONArray testArray = new JSONArray();
		testArray.put("1");
		JSONObject testObject = testArray.toJSONObject(testArray);
		testString = testObject.toString();
		testList.add(goldDisabledID);
				
		Assert.assertEquals(testList.toString(), DisabledId.createDisabledIdListFromJsonPartResponse(testString).toString());

	}
	
	@Test
	public void testCreateDisabledIdListFromJsonObjectNullObject() throws JSONException {
		
		Assert.assertEquals(testList.toString(), DisabledId.createListFromJsonObject(testDisabledIDJSON).toString());

	}
	
	@Test
	public void testCreateDisabledIdListFromJsonObject() throws JSONException {
		
		testDisabledIDJSON.put("1", false);
		testList.add(goldDisabledID);
				
		Assert.assertEquals(testList.toString(), DisabledId.createListFromJsonObject(testDisabledIDJSON).toString());

	}
	

}
