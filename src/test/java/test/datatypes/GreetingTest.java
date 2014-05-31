package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.Greeting;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class GreetingTest {
	// testGreetings
	Greeting testGreeting = new Greeting(0, "", "System Standard");
	Greeting testGreeting1 = new Greeting(1, "", "testName");

	List<Greeting> resultList = new ArrayList<Greeting>();

	final static String TEST_STRING = "\"greetings\":[{\"id\":0,\"name\":\"System Standard\",\"jobberName\":\"\"},{\"id\":1,\"name\":\"testName\",\"jobberName\":\"testJobberName\"}],";

	JsonObject testJsonObject = new JsonObject();


	@Before
	public void setUp() {
		resultList.add(testGreeting);
		resultList.add(testGreeting1);
	}

	@Test
	public void testCompareToOverrideEqual() {

		assertEquals(0, testGreeting.compareTo(testGreeting));
	}

	@Test
	public void testCompareToOverrideGreater() {

		assertEquals(1, testGreeting1.compareTo(testGreeting));
	}

	@Test
	public void testCompareToOverrideLesser() {

		assertEquals(-1, testGreeting.compareTo(testGreeting1));
	}

	@Test
	public void testCreateGroupSettingsFromJsonResponse() {

		assertEquals(resultList.toString(), Greeting.createGroupSettingsFromJsonResponse(TEST_STRING).toString());
	}

	@Test
	public void testCreateListFromJsonObjectHasGreetings() {

		final JsonObject testJSONGreeting = new JsonObject();
		final JsonObject testJSONGreeting1 = new JsonObject();
		final JsonArray testGreetingArray = new JsonArray();
		testJSONGreeting.addProperty("id", 0);
		testJSONGreeting.addProperty("name", "System Standard");
		testJSONGreeting.addProperty("jobberName", "");
		testJSONGreeting1.addProperty("id", 1);
		testJSONGreeting1.addProperty("name", "testName");
		testJSONGreeting1.addProperty("jobberName", "");
		testGreetingArray.add(testJSONGreeting);
		testGreetingArray.add(testJSONGreeting1);
		testJsonObject.add("greetings", testGreetingArray);

		assertEquals(resultList.toString(), Greeting.createListFromJsonObject(testJsonObject).toString());

	}

	@Test
	public void testCreateListFromJsonObjectNoGreetings() {

		final List<Greeting> noResultList = new ArrayList<>();

		assertEquals(noResultList.toString(), Greeting.createListFromJsonObject(testJsonObject).toString());

	}

}
