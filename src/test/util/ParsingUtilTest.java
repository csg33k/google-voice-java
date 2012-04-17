package test.util;

import java.util.ArrayList;
import java.util.List;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * 
 * @author Brett Futral @ Catalyst IT Services
 *
 */
// Coverage Holes:
//	removeUninterestingParts line 41 unable to make null.
//	removeUninterestingParts line 48 unable to make null.
public class ParsingUtilTest {
	
	JSONArray testJSONArray = new JSONArray();
	JSONObject testJSONObject = new JSONObject();

	@Test
	public void testRemoveUninterestingPartsNullEndBorder() {

		Assert.assertEquals(null, ParsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are", null, true));
	}

	@Test
	public void testRemoveUninterestingPartsNullStartBorder() {

		Assert.assertEquals(null, ParsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", null, "?", true));
	}

	@Test
	public void testRemoveUninterestingPartsNullText() {

		Assert.assertEquals(null, ParsingUtil.removeUninterestingParts(null,
				"How are", "?", true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersNoSuchStartIndex() {

		Assert.assertNull(ParsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "Does Not Exist", "?",
				true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersNoSuchEndIndex() {

		Assert.assertNull(ParsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are",
				"Does not exist", true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBorders() {

		Assert.assertEquals("How are you today?", ParsingUtil
				.removeUninterestingParts(
						"Hello Toby, How are you today? Fine.", "How are", "?",
						true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersFalse() {

		Assert.assertEquals(" you today", ParsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are", "?", false));
	}

	@Test
	public void testJsonIntArrayToIntArrayPass() {
		
		testJSONArray.put(1);
		testJSONArray.put(2);

		final int[] testArray = { 1, 2 };

		Assert.assertArrayEquals(testArray,
				ParsingUtil.jsonIntArrayToIntArray(testJSONArray));

	}

	@Test
	public void testJsonStringArrayToStringArrayPass() {

		testJSONArray.put("one");

		final String[] testArray = { "one" };

		final String[] testJSONStringArray = ParsingUtil
				.jsonStringArrayToStringArray(testJSONArray);

		Assert.assertEquals(testArray[0], testJSONStringArray[0]);
	}

	@Test
	public void testJsonStringArrayToStringList() throws JSONException {

		testJSONArray.put("one");
		testJSONArray.put("two");

		testJSONObject.put("testKey", testJSONArray);

		List<String> testInputList = new ArrayList<String>();
		List<String> testList = new ArrayList<String>();
		testList.add("one");
		testList.add("two");

		Assert.assertEquals(
				testList.toString(),
				ParsingUtil.jsonStringArrayToStringList(testJSONObject,
						testInputList, "testKey").toString());

	}
	
	@Test
	public void testJsonIntArrayToIntegerList() throws JSONException {
		
		testJSONArray.put(1);
		testJSONArray.put(2);

		testJSONObject.put("testKey", testJSONArray);

		List<Integer> testInputList = new ArrayList<Integer>();
		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(2);

		Assert.assertEquals(
				testList.toString(),
				ParsingUtil.jsonIntArrayToIntegerList(testJSONObject,
						testInputList, "testKey").toString());
		
	}

}
