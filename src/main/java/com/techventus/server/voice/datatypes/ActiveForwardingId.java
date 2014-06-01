/**
 *
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 *
 */
public class ActiveForwardingId {
	String id;
	boolean disabled;

	public ActiveForwardingId(JsonObject jsonObject) {
		id = jsonObject.get("id").getAsString();
		disabled = jsonObject.get("disabled").getAsBoolean();
	}
	public ActiveForwardingId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}

	public final static List<ActiveForwardingId> createActiveForwardingIdListFromJsonPartResponse(String jsonPart){
		//TODO do with json parser
		List<ActiveForwardingId> activeForwardingIds = new ArrayList<>();
		if(!ParsingUtil.isJsonEmpty(jsonPart)) {
			jsonPart = jsonPart.replaceAll(",\"", ",#");
			String[] activeForwardingIdsStrings = jsonPart.split(Pattern.quote(","));
			for (String activeForwardingIdsString : activeForwardingIdsStrings) {
				String gId = ParsingUtil.removeUninterestingParts(activeForwardingIdsString, "\"", "\"", false);
				boolean gState = Boolean.parseBoolean(activeForwardingIdsString.substring(activeForwardingIdsString.indexOf(":") + 1));
				activeForwardingIds.add(new ActiveForwardingId(gId, gState));
			}
		}
		return activeForwardingIds;
	}

	public String toString() {
			return getAsJsonObject().toString();
	}

	public JsonObject getAsJsonObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", id);
		jsonObject.addProperty("disabled", disabled);
		return jsonObject;
	}

	public String getId() {
		return id;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
