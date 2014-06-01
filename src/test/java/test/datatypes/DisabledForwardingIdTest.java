package test.datatypes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.techventus.server.voice.datatypes.DisabledForwardingId;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brett Futral @ Catalyst IT Services
 */
public class DisabledForwardingIdTest {

	// Golden DisabledForwardingId Object
	final DisabledForwardingId goldDFId = new DisabledForwardingId(
			"1", true);

	// test Objects
	DisabledForwardingId testDFId;
	JsonObject testJsonDFId = new JsonObject();
	JsonArray testJSArray = new JsonArray();

	@Test
	public void testDisabledForwardingNullObjectSaveModeTrue() {

		testDFId = new DisabledForwardingId(
				testJsonDFId, true);

		Assert.assertEquals("{id=null;disabled=false}", testDFId.toString());
	}

	@Test
	public void testDisabledForwardingNullObjectSaveModefalse() {

		try {
			testDFId = new DisabledForwardingId(
					testJsonDFId, false);
		} catch (Exception e) {
			testDFId = goldDFId;
		}

		Assert.assertEquals(goldDFId, testDFId);

	}

	@Test
	public void testDisabledForwardingIdSaveModeFalse() {

		testJsonDFId.addProperty("id", "1");
		testJsonDFId.addProperty("disabled", true);

		testDFId = new DisabledForwardingId(
				testJsonDFId, false);

		final boolean test = goldDFId.toString().equals(
				testDFId.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testDisabledForwardingIdTrueSaveModeTrue() {

		testJsonDFId.addProperty("id", "1");
		testJsonDFId.addProperty("disabled", true);

		testDFId = new DisabledForwardingId(
				testJsonDFId, true);

		final boolean test = goldDFId.toString().equals(
				testDFId.toString());

		Assert.assertEquals(true, test);
	}

	@Test
	public void testCreateDisabledForwardingIdListFromJsonPartResponse() {

		final List<DisabledForwardingId> testList = new ArrayList<>();
		testList.add(goldDFId);

		testJSArray.add(new JsonPrimitive(1));

		String raw = DisabledForwardingId
						.createDisabledForwardingIdListFromJsonPartResponse(
								testJSArray.toString()).toString();

		Assert.assertEquals( testList.toString(), raw);
	}

	@Test
	public void testCreateDisabledForwardingIdArrayFromJsonPartResponse() {

		final DisabledForwardingId[] goldArray = {goldDFId};

		testJSArray.add(new JsonPrimitive(1));

		Assert.assertEquals(
				goldArray[0].toString(),
				DisabledForwardingId
						.createDisabledForwardingIdArrayFromJsonPartResponse(testJSArray.toString())[0].toString());

	}

	@Test
	public void testArrayToJsonObject() {

		final List<DisabledForwardingId> testList = new ArrayList<>();

		testList.add(goldDFId);

		testJsonDFId.addProperty("1", true);

		Assert.assertEquals(testJsonDFId.toString(), DisabledForwardingId
				.arrayToJsonObject(testList).toString());

	}

}
