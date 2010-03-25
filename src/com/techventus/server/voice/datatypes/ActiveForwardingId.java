/**
 * 
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.techventus.server.voice.util.ParsingUtil;

/**
 *
 */
public class ActiveForwardingId {
	String id;
	boolean disabled;
	public ActiveForwardingId(String pId, boolean pDisabled) {
		id = pId;
		disabled = pDisabled;
	}
	public String toString() {
		String ret="{id="+id+";";
		ret+="disabled="+disabled+"}";	
		return ret;
	}
	public final static List<ActiveForwardingId> createActiveForwardingIdListFromJsonPartResponse(String jsonPart) { 
		List<ActiveForwardingId> activeForwardingIds = new ArrayList<ActiveForwardingId>();
		if(jsonPart!=null &! jsonPart.equals("")) {
			jsonPart = jsonPart.replaceAll(",\"", ",#");
			String[] activeForwardingIdsStrings = jsonPart.split(Pattern.quote(","));
			for (int j = 0; j < activeForwardingIdsStrings.length; j++) {			
				String gId = ParsingUtil.removeUninterestingParts(activeForwardingIdsStrings[j], "\"", "\"", false);
				boolean gState = Boolean.parseBoolean(activeForwardingIdsStrings[j].substring(activeForwardingIdsStrings[j].indexOf(":")+1));
				activeForwardingIds.add(new ActiveForwardingId(gId, gState));
			}
		}
		return activeForwardingIds;
	}
	/**
	 * @return "1":true
	 */
	public String toJson() {
		return "\""+id+"\":"+disabled;
	}
}
