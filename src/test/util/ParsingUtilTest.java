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
 * @author bFutral
 * 
 */
// Coverage Holes:
//	removeUninterestingParts line 41 unable to make null.
//	removeUninterestingParts line 48 unable to make null.
@SuppressWarnings("static-access")
public class ParsingUtilTest {

	ParsingUtil parsingUtil;

	@Test
	public void testRemoveUninterestingPartsNullEndBorder() {

		Assert.assertEquals(null, parsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are", null, true));
	}

	@Test
	public void testRemoveUninterestingPartsNullStartBorder() {

		Assert.assertEquals(null, parsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", null, "?", true));
	}

	@Test
	public void testRemoveUninterestingPartsNullText() {

		Assert.assertEquals(null, parsingUtil.removeUninterestingParts(null,
				"How are", "?", true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersNoSuchStartIndex() {

		Assert.assertNull(parsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "Does Not Exist", "?",
				true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersNoSuchEndIndex() {

		Assert.assertNull(parsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are",
				"Does not exist", true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersTrue() {

		Assert.assertEquals("How are you today?", parsingUtil
				.removeUninterestingParts(
						"Hello Toby, How are you today? Fine.", "How are", "?",
						true));
	}

	@Test
	public void testRemoveUninterestingPartsIncludeBordersFalse() {

		Assert.assertEquals(" you today", parsingUtil.removeUninterestingParts(
				"Hello Toby, How are you today? Fine.", "How are", "?", false));
	}

	@Test
	public void testJsonIntArrayToIntArrayPass() {

		final JSONArray testJSONArray = new JSONArray();
		testJSONArray.put(1);
		testJSONArray.put(2);

		final int[] testArray = { 1, 2 };

		Assert.assertArrayEquals(testArray,
				parsingUtil.jsonIntArrayToIntArray(testJSONArray));

	}

	@Test
	public void testJsonStringArrayToStringArrayPass() {

		final JSONArray testJSONArray = new JSONArray();
		testJSONArray.put("one");
		testJSONArray.put("two");

		final String[] testArray = { "one", "two" };

		final String[] testJSONStringArray = parsingUtil
				.jsonStringArrayToStringArray(testJSONArray);

		Assert.assertEquals(testArray[0], testJSONStringArray[0]);
		Assert.assertEquals(testArray[1], testJSONStringArray[1]);
	}

	@Test
	public void testJsonStringArrayToStringList() throws JSONException {

		final JSONArray testJSONArray = new JSONArray();
		testJSONArray.put("one");
		testJSONArray.put("two");

		final JSONObject testJSONObject = new JSONObject();
		testJSONObject.put("testKey", testJSONArray);

		final List<String> testInputList = new ArrayList<String>();
		List<String> testList = new ArrayList<String>();
		testList.add("one");
		testList.add("two");

		Assert.assertEquals(
				testList.toString(),
				parsingUtil.jsonStringArrayToStringList(testJSONObject,
						testInputList, "testKey").toString());

	}
	
	@Test
	public void testJsonIntArrayToIntegerList() throws JSONException {
		
		final JSONArray testJSONArray = new JSONArray();
		testJSONArray.put(1);
		testJSONArray.put(2);

		JSONObject testJSONObject = new JSONObject();
		testJSONObject.put("testKey", testJSONArray);

		List<Integer> testInputList = new ArrayList<Integer>();
		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(2);

		Assert.assertEquals(
				testList.toString(),
				parsingUtil.jsonIntArrayToIntegerList(testJSONObject,
						testInputList, "testKey").toString());
		
	}

}
