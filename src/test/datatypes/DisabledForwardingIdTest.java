package test.datatypes;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledForwardingId;

public class DisabledForwardingIdTest {

	DisabledForwardingId testDisabledForwardingId = new DisabledForwardingId("testID", true);
	
	@Test
	public void testDisabledForwardingIdJSON() throws JSONException {
		
		JSONObject testJSONDisabledForwardingId1 = new JSONObject();
		testJSONDisabledForwardingId1.put("id", "testID");
		testJSONDisabledForwardingId1.put("disabled", true);
		
		DisabledForwardingId testDisabledForwardingId1 = new DisabledForwardingId(testJSONDisabledForwardingId1, false);
		
		boolean test = (testDisabledForwardingId.toString().equals(testDisabledForwardingId1.toString()));
		
		Assert.assertEquals(true, test);
	}
	
	@Test 
	public void testDisabledForwardingIdJSONSaveModeFalse() throws JSONException {
		
		JSONObject testJSONDisabledForwardingId2 = new JSONObject();
		testJSONDisabledForwardingId2.put("id", "testID");
		testJSONDisabledForwardingId2.put("disabled", false);
		
		DisabledForwardingId testDisabledForwardingId2 = new DisabledForwardingId(testJSONDisabledForwardingId2, true);	

		boolean test = (testDisabledForwardingId.toString().equals(testDisabledForwardingId2.toString()));
		
		Assert.assertEquals(false, test);	}

}
