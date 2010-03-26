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
 *
 */
public class EmailAddress {
	String address;
	public EmailAddress(String pId) {
		address = pId;
	}
	public String toString() {
		String ret="{address="+address+"}";	
		return ret;
	}
	public final static List<EmailAddress> createEmailAddressListFromJsonPartResponse(String jsonPart) { 
		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
		if(jsonPart!=null &! jsonPart.equals("")) {
			jsonPart = jsonPart.replaceAll(",\"", ",#");
			String[] emailAddressesStrings = jsonPart.split(Pattern.quote(","));
			for (int j = 0; j < emailAddressesStrings.length; j++) {			
				String gId = ParsingUtil.removeUninterestingParts(emailAddressesStrings[j], "\"", "\"", false);
				emailAddresses.add(new EmailAddress(gId));
			}
		}
		return emailAddresses;
	}

	public String toJson() {
		return "\""+address+"\"";
	}
	/**
	 * 
	 * @param settingsJSON
	 * @return
	 */
	public final static List<EmailAddress> createListFromJsonObject(JSONObject settingsJSON) { 
		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
		if(settingsJSON.has("emailAddresses")) {
			try {
				JSONArray lArray = (JSONArray) settingsJSON.get("emailAddresses");
				
				for (int i = 0; i < lArray.length(); i++) {
					emailAddresses.add(new EmailAddress(lArray.getString(i)));
				}
			} catch (JSONException e1) {
				// Nothing - will return empty array at exception
			}

		}
		
		return emailAddresses;
	}
	//TODO dotn create list first, direct transform
	public final static EmailAddress[] createArrayFromJsonObject(JSONObject settingsJSON) { 
		List<EmailAddress> tList = createListFromJsonObject(settingsJSON);
		return (EmailAddress[]) tList.toArray(new EmailAddress[tList.size()]);
	}
}
