/**
 *
 */
package com.techventus.server.voice.datatypes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.techventus.server.voice.util.ParsingUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DisabledForwardingId {
	private Gson gson = new Gson();

	String id;
	boolean disabled;
	public DisabledForwardingId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}
	public DisabledForwardingId(JsonObject jsonObject, boolean saveMode) {
		if(!saveMode || saveMode && jsonObject.has("id")) id = jsonObject.get("id").getAsString();
		if(!saveMode || saveMode && jsonObject.has("disabled")) disabled = jsonObject.get("disabled").getAsBoolean();
	}
	public String toString() {
		String ret="{id="+id+";";
		ret+="disabled="+disabled+"}";
		return ret;
	}
	public static List<DisabledForwardingId> createDisabledForwardingIdListFromJsonPartResponse(String jsonPart) {
		if(ParsingUtil.isJsonEmpty(jsonPart))
			return Collections.EMPTY_LIST;

		List<DisabledForwardingId> disabledForwardingIds = new ArrayList<>();
		try {
			Gson gson = new Gson();
			String[] ids = gson.fromJson(jsonPart, String[].class);
			for (String id : ids) {
				DisabledForwardingId disabledForwardingId = new DisabledForwardingId(id, true);
				disabledForwardingIds.add(disabledForwardingId);
			}
		} catch(Exception e) {
			// nothing on parse error
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
		return gson.toJson(this);
	}

	public JsonObject toJsonObject(){
		return  gson.toJsonTree(this).getAsJsonObject();
	}

	// needs to be {"2": true,"3": true}
	public static Object arrayToJsonObject(List<DisabledForwardingId> disabledForwardingIds) {
		Map<String, Boolean> map = new HashMap<>();
		for (int i = 0; i < disabledForwardingIds.size(); i++) {
			map.put(disabledForwardingIds.get(i).getId()+"",disabledForwardingIds.get(i).isDisabled());
		}
		return new Gson().toJsonTree(map).getAsJsonObject();
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
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


}
