package test.datatypes;

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
public class DisabledForwardingIdTest {

	//Golden DisabledForwardingId Object
	final DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId(
			"1", true);

	@Test
	public void testDisabledForwardingIdJSONMatchTrue() throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", true);

		final DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		final boolean test = testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId1.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForDisabled()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "1");
		testJSONDisabledForwardingId.put("disabled", false);

		final DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		final boolean test = testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId2.toString());

		Assert.assertEquals(false, test);
	}

	@Test
	public void testDisabledForwardingIdJSONSMatchFalseForID()
			throws JSONException {

		JSONObject testJSONDisabledForwardingId = new JSONObject();
		testJSONDisabledForwardingId.put("id", "2");
		testJSONDisabledForwardingId.put("disabled", true);

		final DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(
				testJSONDisabledForwardingId, false);

		final boolean test = testDisabledForwardingId.toString()
				.equals(testDisabledForwardingId2.toString());

		Assert.assertEquals(false, test);
	}

}
