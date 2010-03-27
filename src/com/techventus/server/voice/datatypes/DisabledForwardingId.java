/**
 * 
 */
package com.techventus.server.voice.datatypes;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.techventus.server.voice.util.ParsingUtil;

public class DisabledForwardingId {
	String id;
	boolean disabled;
	public DisabledForwardingId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}
	public DisabledForwardingId(JSONObject jsonObject, boolean saveMode) throws JSONException {
		if(!saveMode || saveMode && jsonObject.has("id")) id = jsonObject.getString("id");
		if(!saveMode || saveMode && jsonObject.has("disabled")) disabled = jsonObject.getBoolean("disabled");
	}
	public String toString() {
		String ret="{id="+id+";";
		ret+="disabled="+disabled+"}";	
		return ret;
	}
	public final static List<DisabledForwardingId> createDisabledForwardingIdListFromJsonPartResponse(String jsonPart) { 	
		List<DisabledForwardingId> disabledForwardingIds = new ArrayList<DisabledForwardingId>();
		if(jsonPart!=null &! jsonPart.equals("")) {
			jsonPart = jsonPart.replaceAll(",\"", ",#");
			String[] disabledForwardingIdsStrings = jsonPart.split(Pattern.quote(","));
			for (int j = 0; j < disabledForwardingIdsStrings.length; j++) {			
				String gId = ParsingUtil.removeUninterestingParts(disabledForwardingIdsStrings[j], "\"", "\"", false);
				boolean gState = Boolean.parseBoolean(disabledForwardingIdsStrings[j].substring(disabledForwardingIdsStrings[j].indexOf(":")+1));
				if(gId!=null) {
					DisabledForwardingId dis = new DisabledForwardingId(gId, gState);
					disabledForwardingIds.add(dis);
				}
			}
		}
		return disabledForwardingIds;
	}
	
	public final static DisabledForwardingId[] createDisabledForwardingIdArrayFromJsonPartResponse(String jsonPart) {
		List<DisabledForwardingId> list = createDisabledForwardingIdListFromJsonPartResponse(jsonPart);
		DisabledForwardingId[] result = new DisabledForwardingId[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
	
	public String toJson(){	
		return toJsonObject().toString();
	}
	
	public JSONObject toJsonObject(){
		JSONObject resultO = new JSONObject();
		try { 		
			resultO.putOpt("id", id);
			resultO.putOpt("disabled", disabled);
		} catch (JSONException e) {
			return null;
		}
		
		return resultO;
	}

	// needs to be {"2": true,"3": true}
	public static Object arrayToJsonObject(List<DisabledForwardingId> disabledForwardingIds) throws JSONException {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < disabledForwardingIds.size(); i++) {
			obj.put(disabledForwardingIds.get(i).getId()+"",disabledForwardingIds.get(i).isDisabled());
		}
		return obj;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
	
	
}	
