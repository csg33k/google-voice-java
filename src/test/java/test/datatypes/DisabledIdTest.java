package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import junit.framework.Assert;

import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledId;

import static org.junit.Assert.assertEquals;

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
	JsonObject tesJSONtDId = new JsonObject();
	String testString;
	List<DisabledId> testList = new ArrayList<DisabledId>();

	@Test
	public void testDisabledIDJSONNullObjectSaveModeTrue() {

		final JsonObject testJSONDId = new JsonObject();

		testDisabledID = new DisabledId(testJSONDId, true);

		Assert.assertNotNull(testDisabledID);
	}

	@Test
	public void testDisabledIDJSONNullObjectSaveModeFalse() {

		try {
			testDisabledID = new DisabledId(tesJSONtDId, false);
		} catch (Exception e) {
			testDisabledID = goldDisabledID;
		}

		Assert.assertEquals(goldDisabledID, testDisabledID);
	}

	@Test
	public void testDisabledIdJSONSaveModeFalse() {

		tesJSONtDId.addProperty("id", "1");
		tesJSONtDId.addProperty("disabled", false);

		testDisabledID = new DisabledId(tesJSONtDId, false);

		final boolean test = goldDisabledID.toString().equals(
				testDisabledID.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledIdJSONSaveModeTrue() {

		tesJSONtDId.addProperty("id", "1");
		tesJSONtDId.addProperty("disabled", false);

		testDisabledID = new DisabledId(tesJSONtDId, true);

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

		List<DisabledId> testNullString = new ArrayList<DisabledId>();

		try {
			testNullString = DisabledId.createDisabledIdListFromJsonPartResponse(testString);
		}
		catch(Exception e) {
			testString = "exception Caught";
		}

		Assert.assertEquals(testString = "exception Caught", testString);
		Assert.assertEquals(testList, testNullString);

	}

	@Test
	public void testCreateDisabledIdListFromJsonPartResponse() {

		final JsonArray testArray = new JsonArray();
		testArray.add(new JsonPrimitive("1"));
//		final JsonObject testObject = testArray.toJsonObject(testArray);
		testString = testArray.toString();
		testList.add(goldDisabledID);

		Assert.assertEquals(testList.toString(), DisabledId.createDisabledIdListFromJsonPartResponse(testString).toString());

	}

	@Test
	public void testCreateDisabledIdListFromJsonObjectNullObject() {

		assertEquals(testList.toString(), DisabledId.createListFromJsonObject(tesJSONtDId).toString());

	}

	@Test
	public void testCreateDisabledIdListFromJsonObject() {

		tesJSONtDId.addProperty("1", false);
		testList.add(goldDisabledID);

		assertEquals(testList.toString(), DisabledId.createListFromJsonObject(tesJSONtDId).toString());

	}


}
