/**
 * 
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * Holds the settings of the disabledIdMap json object
 */
public class DisabledId {
	String id;
	boolean disabled;
	public DisabledId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}
	public String toString() {
		String ret="{id="+id+";";
		ret+="disabled="+disabled+"}";	
		return ret;
	}
	public final static List<DisabledId> createDisabledIdListFromJsonPartResponse(String jsonPart) { 
		List<DisabledId> disabledIds = new ArrayList<DisabledId>();
		if(jsonPart!=null &! jsonPart.equals("")) {
			jsonPart = jsonPart.replaceAll(",\"", ",#");
			String[] disabledIdsStrings = jsonPart.split(Pattern.quote(","));
			for (int j = 0; j < disabledIdsStrings.length; j++) {			
				String gId = ParsingUtil.removeUninterestingParts(disabledIdsStrings[j], "\"", "\"", false);
				boolean gState = Boolean.parseBoolean(disabledIdsStrings[j].substring(disabledIdsStrings[j].indexOf(":")+1));
				disabledIds.add(new DisabledId(gId, gState));
			}
		}
		return disabledIds;
	}
	public final static List<DisabledId> createListFromJsonObject(JSONObject settingsJSON) { 
		List<DisabledId> disabledIds = new ArrayList<DisabledId>();
		if(settingsJSON.has("disabledIdMap")) {
			JSONObject lObject;
			try {
				lObject = (JSONObject) settingsJSON.get("disabledIdMap");
				String[] objectNames = JSONObject.getNames(lObject);
				
				for (int i = 0; i < objectNames.length; i++) {
					String lId = objectNames[i];
					boolean lDisabled;
					try {
						lDisabled = lObject.getBoolean(lId);
						disabledIds.add(new DisabledId(lId, lDisabled));
					} catch (JSONException e) {
						// Nothing - will not add at exception
					}
				}
			} catch (JSONException e1) {
				// Nothing - will return empty array at exception
			}

		}
		
		return disabledIds;
	}
	//TODO dotn create list first, direct transform
	public final static DisabledId[] createArrayFromJsonObject(JSONObject settingsJSON) { 
		List<DisabledId> tList = createListFromJsonObject(settingsJSON);
		return (DisabledId[]) tList.toArray(new DisabledId[tList.size()]);
	}
	/**
	 * @return "1":true
	 */
	public String toJson() {
		return "\""+id+"\":"+disabled;
	}
	public String getId() {
		return id;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public JSONObject toJsonObject(){
		JSONObject resultO = new JSONObject();
		try { 		
			resultO.accumulate(id, disabled);
		} catch (JSONException e) {
			return null;
		}
		
		return resultO;
	}
	
}
