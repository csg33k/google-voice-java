package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;

/**
 * 
 * @author bFutral
 * 
 */
// Holes in Coverage
// DisabledForwardingId.DisabledForwardingId line 23 & 25 missing 1 of 6
// branches for both lines due to error if save mode is false AND id or Disabled
// is missing
public class DisabledForwardingIdTest {

	// Golden DisabledForwardingId Object
	final DisabledForwardingId goldDisabledForwardingId = new DisabledForwardingId(
			"1", true);

	@Test
	public void testDisabledForwardingIdNullDisabledSaveModeTrue()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "1");

		final DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId(
				testJSONDisabledForwardingId, true);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId.toString());

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdNullIDSaveModeTrue()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId1 = new JSONObject();
		testJSONDisabledForwardingId1.put("disabled", false);

		final DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId(
				testJSONDisabledForwardingId1, true);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId1.toString());

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForDisabled()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId2 = new JSONObject();
		testJSONDisabledForwardingId2.put("id", "1");
		testJSONDisabledForwardingId2.put("disabled", false);

		final DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(
				testJSONDisabledForwardingId2, false);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId2.toString());

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForID()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId3 = new JSONObject();
		testJSONDisabledForwardingId3.put("id", "2");
		testJSONDisabledForwardingId3.put("disabled", true);

		final DisabledForwardingId testDisabledForwardingId3 = new DisabledForwardingId(
				testJSONDisabledForwardingId3, false);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId3.toString());

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdJSONMatchTrue() throws JSONException {

		JSONObject testJSONDisabledForwardingId4 = new JSONObject();
		testJSONDisabledForwardingId4.put("id", "1");
		testJSONDisabledForwardingId4.put("disabled", true);

		final DisabledForwardingId testDisabledForwardingId4 = new DisabledForwardingId(
				testJSONDisabledForwardingId4, false);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId4.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testCreateDisabledForwardingIdListFromJsonPartResponse()
			throws JSONException {

		List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
		testList.add(goldDisabledForwardingId);

		JSONArray testJSArray1 = new JSONArray();
		testJSArray1.put(1);
		JSONObject disabledFIDs = testJSArray1.toJSONObject(testJSArray1);

		Assert.assertEquals(
				testList.toString(),
				DisabledForwardingId
						.createDisabledForwardingIdListFromJsonPartResponse(
								disabledFIDs.toString()).toString());
	}

	@Test
	public void testCreateDisabledForwardingIdArrayFromJsonPartResponse()
			throws JSONException {

		DisabledForwardingId[] goldArray = { goldDisabledForwardingId };

		JSONArray testJSArray = new JSONArray();
		testJSArray.put(1);
		JSONObject disabledFIDs = testJSArray.toJSONObject(testJSArray);

		Assert.assertEquals(
				goldArray[0].toString(),
				DisabledForwardingId
						.createDisabledForwardingIdArrayFromJsonPartResponse(disabledFIDs
								.toString())[0].toString());

	}

	@Test
	public void testArrayToJsonObject() throws JSONException {

		List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
		testList.add(goldDisabledForwardingId);

		Assert.assertEquals("{\"1\":true}",
				DisabledForwardingId.arrayToJsonObject(testList).toString());

	}

}
