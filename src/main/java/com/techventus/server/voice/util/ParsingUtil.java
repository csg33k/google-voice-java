package com.techventus.server.voice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Collection of useful html parsing methods
 *
 * @author Tobias Eisentraeger
 *
 */
public abstract class ParsingUtil {

	/**
	 * Strips the text from the uninteresting parts before and after the interesting part.
	 * The return includes borders if includeBorders == true - Returns null when Exception occures<br/><br/>
	 * Example:<br/>
	 * removeUninterestingParts("Hello Toby  , How are you today? Fine.", "How are", "?" , <b>true</b>)<br/>
	 * Returns: "How are you today?"<br/>
	 * <br/>
	 * removeUninterestingParts("Hello Joseph, How are you today? Fine.", "How are", "?" , <b>false</b>)<br/>
	 * Returns: " you today"
	 *
	 * @param text the text
	 * @param startBorder the start border
	 * @param endBorder the end border
	 * @param includeBorders the include borders
	 * @return the string
	 */
	public static final String removeUninterestingParts(String text, String startBorder, String endBorder, boolean includeBorders) {
		String ret="";
		try {
			if(text!=null&&startBorder!=null&&endBorder!=null&&(text.indexOf(startBorder)!=-1)&&(text.indexOf(endBorder)!=-1) ) {

				if(includeBorders) {
					text = text.substring(text.indexOf(startBorder));
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder)+endBorder.length());
					} else {
						ret = null;
					}
				} else {
					text = text.substring(text.indexOf(startBorder)+startBorder.length());
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder));
					} else {
						ret = null;
					}
				}

			} else {
				ret = null;
			}
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
			System.out.println("Begin:"+startBorder);
			System.out.println("End:"+endBorder);
			System.out.println("Text:"+text);
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}


	//TODO use Apache commons StringEscapeUtils.unescapeHTML() ?
	/**
	 * Replaces some speciel htmlEntities with a corresponding String.
	 *
	 * @param s the HTML Entity in String format
	 * @return the Decoded HTML in String format
	 */
	public static String htmlEntitiesDecode(String s) {
		s=s.replaceAll("&#39;", "'");
		return s;
	}

	public static boolean isJsonEmpty(String value) {
		return StringUtils.isEmpty(value) || "{}".equals(value);
	}

	/**
	 * Json int array to int array.
	 *
	 * @param array the array
	 * @return the int[]
	 */
	public static final int[] jsonIntArrayToIntArray(JsonArray array) {
		int[] result = new int[array.size()];
		for (int i = 0; i < array.size(); i++) {
				result[i] = array.get(i).getAsInt();
		}
		return result;
	}


	public static List<String> names(JsonObject object) {

		Set<Map.Entry<String, JsonElement>> set = object.entrySet();
		List<String> list = Lists.newArrayList();
		for(Map.Entry<String, JsonElement> entry : set) {
			list.add(entry.getKey());
		}

		return list;
	}

	/**
	 * Json string array to string array.
	 *
	 * @param array the array
	 * @return the string[]
	 */
	public static String[] jsonStringArrayToStringArray(JsonArray array) {
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i).getAsString();
		}
		return result;
	}

	/**
	 * Json string array to string list.
	 *
	 * @param settingsJSON the settings json
	 * @param stringList the string list
	 * @param key the key
	 * @return the list
	 */
	public static final List<String> jsonStringArrayToStringList(JsonObject settingsJSON, List<String> stringList, String key) {
		stringList = new ArrayList<>();
		for (int i = 0; i < (settingsJSON.get(key)).getAsJsonArray().size(); i++) {
			stringList.add((settingsJSON.get(key)).getAsJsonArray().get(i).getAsString());
		}
		return stringList;
	}

	/**
	 * Converts a Json Integer array to an ArrayList of Integers.
	 *
	 * @param settingsJSON the settings json
	 * @param integerList the integer list
	 * @param key the key corresponding to the JSON formatted integer array
	 * @return the list
	 */
	public static final List<Integer> jsonIntArrayToIntegerList(JsonObject settingsJSON, List<Integer> integerList, String key) {
		//TODO Why are we taking integerList as input, if we replace with new one?
		integerList = new ArrayList<>();
		for (int i = 0; i < (settingsJSON.get(key)).getAsJsonArray().size(); i++) {
			integerList.add((settingsJSON.get(key)).getAsJsonArray().get(i).getAsInt());
		}
		return integerList;
	}

	/**
	 * String list to JSON array.
	 *
	 * @param stringList the string list input
	 * @return the JSON array
	 */
	public static final JsonArray stringListToJsonArray(List<String> stringList) {
		JsonArray jArray = new JsonArray();
		for(String element : stringList) {
			jArray.add(new JsonPrimitive(element));
		}

		return jArray;
	}


}