/**
 * 
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
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
				DisabledForwardingId dis = new DisabledForwardingId(gId, gState);
				disabledForwardingIds.add(dis);
			}
		}
		return disabledForwardingIds;
	}
	/**
	 * @return "1":true
	 */
	public String toJson() {
		return "\""+id+"\":"+disabled;
	}
}	
