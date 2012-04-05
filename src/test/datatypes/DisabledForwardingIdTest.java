package test.datatypes;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;

public class DisabledForwardingIdTest {

	DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId(
			"1", true);

	@Test
	public void testDisabledForwardingIdJSONMatchTrue() throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", true);

		DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		boolean test = (testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId1.toString()));

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForDisabled()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", false);

		DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		boolean test = (testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId2.toString()));

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForID()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "2");
		testJSONDisabledForwardingId.put("disabled", true);

		DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		boolean test = (testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId2.toString()));

		Assert.assertEquals(false, test);
	}

}
