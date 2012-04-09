package test.datatypes;

import junit.framework.Assert;

import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Test;

import com.techventus.server.voice.datatypes.DisabledId;

/**
 * 
 * @author bFutral
 *
 */
public class DisabledIdTest {
	
	//Golden DisabledId Object
	final DisabledId testDisabledID = new DisabledId("1", false);

	@Test
	public void testDisabledIdJSONTrue() throws JSONException {
		JSONObject testDisabledIDJSON1 = new JSONObject();
		testDisabledIDJSON1.put("id", "1");
		testDisabledIDJSON1.put("disabled", false);
		
		final DisabledId testDisabledID1 = new DisabledId(testDisabledIDJSON1, false);
		
		final boolean test = testDisabledID.toString().equals(testDisabledID1.toString());
			
		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testDisabledIDJSONFalseID() throws JSONException {
		JSONObject testDisabledIDJSON2 = new JSONObject();
		testDisabledIDJSON2.put("id", "2");
		testDisabledIDJSON2.put("disabled", false);
		
		final DisabledId testDisabledID2 = new DisabledId(testDisabledIDJSON2, false);
		
		final boolean test = testDisabledID.toString().equals(testDisabledID2.toString());
		
		Assert.assertEquals(false, test);
	}
	
	@Test
	public void testDisabledIDJSONFalseNotDisabled() throws JSONException {
		JSONObject testDisabledIDJSON3 = new JSONObject();
		testDisabledIDJSON3.put("id", "1");
		testDisabledIDJSON3.put("disabled", true);
		
		final DisabledId testDisabledID2 = new DisabledId(testDisabledIDJSON3, false);
		
		final boolean test = testDisabledID.toString().equals(testDisabledID2.toString());
		
		Assert.assertEquals(false, test);
	}

}
