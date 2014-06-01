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
 * TODO support multiple email adresses, since its an array (but usually only has one address in there)
 */
public class EmailAddress {
	String address;

	/**
	 *
	 * @param pId
	 */
	public EmailAddress(String pId) {
		address = pId;
	}

	public String toString() {
		String ret="{address="+address+"}";
		return ret;
	}

	/**
	 *
	 * @param phonesJSON
	 * @throws IllegalStateException
	 */
	public EmailAddress(JsonObject phonesJSON) throws IllegalStateException {
		if (phonesJSON.has("emailAddresses")) {
			address = phonesJSON.get("emailAddresses").toString();
		}
	}

	/**
	 *
	 * @param jsonPart
	 * @return List<EmailAddress>
	 */
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
		return address;
	}

	/**
	 *
	 * @param settingsJSON
	 * @return EmailAddress[]
	 */
	public final static EmailAddress[] createArrayFromJsonObject(JsonObject settingsJSON) {
		JsonArray  addresses;
		EmailAddress[] ret;
		try {
		addresses = settingsJSON.get("emailAddresses").getAsJsonArray();
		ret = new EmailAddress[addresses.size()];
		for (int i = 0; i < addresses.size(); i++) {
			ret[i] = new EmailAddress(addresses.get(i).getAsString());
		}
		} catch (IllegalStateException e) {
			try {
				String lAddress = settingsJSON.get("emailAddresses").getAsString();
				ret = new EmailAddress[]{new EmailAddress(lAddress)};
			} catch (Exception e2) {
				ret = new EmailAddress[0];
			}
		}
		return ret;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
