package test.datatypes;

import junit.framework.Assert;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledId;

public class DisabledIdTest {
	
	DisabledId testDisabledID = new DisabledId("testID", false);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDisabledIdJSON() throws JSONException {
		JSONObject testDisabledIDJSON1 = new JSONObject();
		testDisabledIDJSON1.put("id", "testID");
		testDisabledIDJSON1.put("disabled", false);
		
		DisabledId testDisabledID1 = new DisabledId(testDisabledIDJSON1, false);
		
		boolean test = (testDisabledID.toString().equals(testDisabledID1.toString()));
			
		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testDisabledIDJSONSaveMode() throws JSONException {
		JSONObject testDisabledIDJSON2 = new JSONObject();
		testDisabledIDJSON2.put("id", "testID");
		testDisabledIDJSON2.put("disabled", true);
		
		DisabledId testDisabledID2 = new DisabledId(testDisabledIDJSON2, true);
		
		boolean test = (testDisabledID.toString().equals(testDisabledID2.toString()));
		
		Assert.assertEquals(false, test);
	}

}
