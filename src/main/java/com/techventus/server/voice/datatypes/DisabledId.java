/**
 *
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * Holds the settings of the disabledIdMap json object
 */
public class DisabledId {
	String id;
	boolean disabled;

	/**
	 *
	 * @param pId
	 * @param pDisabled
	 */
	public DisabledId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}

	/**
	 *
	 * @param jsonObject
	 * @param saveMode
	 */
	public DisabledId(JsonObject jsonObject, boolean saveMode)  {
		if(!saveMode || saveMode && jsonObject.has("id")) id = jsonObject.get("id").getAsString();
		if(!saveMode || saveMode && jsonObject.has("disabled")) disabled = jsonObject.get("disabled").getAsBoolean();
	}

	public String toString() {
		String ret="{id="+id+";";
		ret+="disabled="+disabled+"}";
		return ret;
	}

	/**
	 *
	 * @param jsonPart
	 * @return List<DisabledId>
	 */
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

	/**
	 *
	 * @param disabledIdMapJSON
	 * @return List<DisabledId>
	 */
	public final static List<DisabledId> createListFromJsonObject(JsonObject disabledIdMapJSON) {
		List<DisabledId> disabledIds = new ArrayList<>();

		if(disabledIdMapJSON.isJsonArray()) {
			JsonArray disabledNames = disabledIdMapJSON.isJsonArray() ? disabledIdMapJSON.getAsJsonArray() : null;
			if (disabledNames != null) {
				for (int i = 0; i < disabledNames.size(); i++) {
					String id = disabledNames.get(i).getAsString();
					boolean booleanValue = disabledIdMapJSON.get(id).getAsBoolean();
					disabledIds.add(new DisabledId(id, booleanValue));
				}
			}
		} else {
			try {
				String id = disabledIdMapJSON.entrySet().iterator().next().getKey();
				boolean booleanValue = disabledIdMapJSON.get(id).getAsBoolean();
				disabledIds.add(new DisabledId(id, booleanValue));
			} catch(Exception e ) {
				return disabledIds;
			}
		}
		return disabledIds;
	}

	/**
	 *
	 * @param settingsJSON
	 * @return DisabledId[]
	 */
	//TODO dotn create list first, direct transform
	public final static DisabledId[] createArrayFromJsonObject(JsonObject settingsJSON) {
		List<DisabledId> tList = createListFromJsonObject(settingsJSON.get("disabledIdMap").getAsJsonObject());
		return tList.toArray(new DisabledId[tList.size()]);
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

	public int getIdAsInt() {
		int ret;
		try {
			ret = Integer.parseInt(id);
		} catch (Exception e) {
			ret = -1;
		}
		return ret;
	}

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
