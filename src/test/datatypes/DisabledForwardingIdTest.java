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
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class DisabledForwardingIdTest {

	// Golden DisabledForwardingId Object
	final DisabledForwardingId goldDisabledForwardingId = new DisabledForwardingId(
			"1", true);

	// test Objects
	DisabledForwardingId testDisabledForwardingId;
	JSONObject testJSONDisabledForwardingId = new JSONObject();
	JSONArray testJSArray = new JSONArray();

	@Test
	public void testDisabledForwardingNullObjectSaveModeTrue()
			throws JSONException {

		testDisabledForwardingId = new DisabledForwardingId(
				testJSONDisabledForwardingId, true);

		Assert.assertEquals("{id=null;disabled=false}", testDisabledForwardingId.toString());
	}

	@Test
	public void testDisabledForwardingNullObjectSaveModefalse() {

		try {
			testDisabledForwardingId = new DisabledForwardingId(
					testJSONDisabledForwardingId, false);
		} catch (Exception e) {

		}

		Assert.assertNull(testDisabledForwardingId);

	}

	@Test
	public void testDisabledForwardingIdSaveModeFalse() throws JSONException {

		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", true);

		testDisabledForwardingId = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledForwardingIdTrueSaveModeTrue()
			throws JSONException {

		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", true);

		testDisabledForwardingId = new DisabledForwardingId(
				testJSONDisabledForwardingId, true);

		final boolean test = goldDisabledForwardingId.toString().equals(
				testDisabledForwardingId.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testCreateDisabledForwardingIdListFromJsonPartResponse()
			throws JSONException {

		List<DisabledForwardingId> testList = new ArrayList<DisabledForwardingId>();
		testList.add(goldDisabledForwardingId);

		testJSArray.put(1);
		JSONObject disabledFIDs = testJSArray.toJSONObject(testJSArray);

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

		testJSONDisabledForwardingId.put("1", true);
		
		Assert.assertEquals(testJSONDisabledForwardingId.toString(), DisabledForwardingId
				.arrayToJsonObject(testList).toString());

	}

}
