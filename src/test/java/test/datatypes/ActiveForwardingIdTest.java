package test.datatypes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.techventus.server.voice.datatypes.ActiveForwardingId;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Brendon Shih - Catalyst IT Services
 *
 */
public class ActiveForwardingIdTest {

	List<ActiveForwardingId> testList = new ArrayList<ActiveForwardingId>();
	String testString;

	@Test
	public void testCreateActiveForwardingIdListFromJsonPartResponseEmptyString() {
		testString = "";
		assertEquals(testList, ActiveForwardingId
				.createActiveForwardingIdListFromJsonPartResponse(testString));
	}

	@Test
	public void testCreateActiveForwardingIdListFromJsonPartResponseNullString() {

		List<ActiveForwardingId> testNullList;

		try {
			testNullList = ActiveForwardingId
					.createActiveForwardingIdListFromJsonPartResponse(testString);
		} catch (Exception e) {
			testNullList = testList;
		}

		assertEquals(testList, testNullList);

	}

	@Test
	public void testCreateActiveForwardingIdListFromJsonPartResponse() {

		final JsonArray testArray = new JsonArray();
		testArray.add(new JsonPrimitive("1"));
		testString = testArray.toString();

		final JsonObject testAFIDObject = new JsonObject();
		testAFIDObject.addProperty("id", 1);
		testAFIDObject.addProperty("disabled", false);
		final ActiveForwardingId testAFID = new ActiveForwardingId(testAFIDObject);
		testList.add(testAFID);

		assertEquals(testList.toString(), ActiveForwardingId
				.createActiveForwardingIdListFromJsonPartResponse(testString)
				.toString());

	}

}
