package test.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.EmailAddress;

/**
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class EmailAddressTest {

	String testEmailString = "test@email.com";
	String testString;

	EmailAddress goldEmail = new EmailAddress("test@email.com");
	EmailAddress testEmailAdd;

	List<EmailAddress> testList = new ArrayList<EmailAddress>();

	//Generic Phone JSON construction
	JsonObject testJSONPhone = new JsonObject();

	//JSON Arrays
	JsonArray times = new JsonArray();
	JsonArray emailAddresses = new JsonArray();



	@Before
	public void setUp() {


		emailAddresses.add(new JsonPrimitive("testEmailAddress"));

		testJSONPhone.addProperty("id", 2);
		testJSONPhone.addProperty("name", "testPhone1");
		testJSONPhone.addProperty("phoneNumber", "+15035552121");
		testJSONPhone.addProperty("active", true);
		testJSONPhone.addProperty("behaviorOnRedirect", 1);
		testJSONPhone.addProperty("carrier", "testCarrier");
		testJSONPhone.addProperty("customOverrideState", 1);
		testJSONPhone.addProperty("dEPRECATEDDisabled", false);
		testJSONPhone.addProperty("enabledForOthers", true);
		testJSONPhone.addProperty("formattedNumber", "+15035552121");
		testJSONPhone.addProperty("incomingAccessNumber", "");
		testJSONPhone.addProperty("policyBitmask", 1);
		testJSONPhone.addProperty("redirectToVoicemail", false);
		testJSONPhone.addProperty("scheduleSet", false);
		testJSONPhone.addProperty("smsEnabled", true);
		testJSONPhone.addProperty("telephonyVerified", true);
		testJSONPhone.addProperty("type", 1);
		testJSONPhone.addProperty("verified", true);
		testJSONPhone.addProperty("voicemailForwardingVerified", true);
		testJSONPhone.addProperty("weekdayAllDay", true);
		testJSONPhone.add("weekdayTimes", times);
		testJSONPhone.addProperty("weekendAllDay", true);
		testJSONPhone.add("weekendTimes", times);

	}

	@Test
	public void testEmailAddressNoEmail() {

		testEmailAdd = new EmailAddress(testJSONPhone);

		Assert.assertEquals("{address=null}", testEmailAdd.toString());
	}

	@Test
	public void testEmailAddress() {

		testJSONPhone.add("emailAddresses", emailAddresses);

		testEmailAdd = new EmailAddress(testJSONPhone);
		String result = testEmailAdd.toString();

		Assert.assertEquals("{address=[\"testEmailAddress\"]}", testEmailAdd.toString());
	}

	@Test
	public void testCreateEmailAddressListFromJsonPartResponseEmptyString() {

		testString = "";

		Assert.assertEquals(testList, EmailAddress.createEmailAddressListFromJsonPartResponse(testString));

	}

	@Test
	public void testCreateEmailAddressListFromJsonPartResponseNullString() {

		List<EmailAddress> nullList = new ArrayList<EmailAddress>();

		try {
			nullList = EmailAddress.createEmailAddressListFromJsonPartResponse(testString);
		}
		catch(Exception e) {
			nullList = testList;
		}

		Assert.assertEquals(testList, nullList);

	}

	@Test
	public void testCreateEmailAddressListFromJsonPartResponse() {

		final JsonArray testArray = new JsonArray();
		testArray.add(new JsonPrimitive(testEmailString));
//		final JsonObject testObject = testArray.toJsonObject(testArray);
		testString = testArray.toString();

		testList.add(goldEmail);

		Assert.assertEquals(testList.toString(), EmailAddress.createEmailAddressListFromJsonPartResponse(testString).toString());

	}

	@Test
	public void testCreateArrayFromJsonObject() {

		final JsonArray testArray = new JsonArray();
		testArray.add(new JsonPrimitive(testEmailString));
		final JsonObject testObject = new JsonObject();
		testObject.add("emailAddresses", testArray);

		final EmailAddress[] testEmailArray = {goldEmail};

		Assert.assertEquals(testEmailArray[0].toString(), EmailAddress.createArrayFromJsonObject(testObject)[0].toString());

	}

}
