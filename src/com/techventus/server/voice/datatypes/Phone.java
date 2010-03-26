
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techventus.server.voice.util.ParsingUtil;

public class Phone{
   	//TODO - implement
	
	private final static boolean saveMode = false;
	
	private boolean active;
   	private int behaviorOnRedirect;
   	private String carrier;
   	private int customOverrideState;
   	private boolean dEPRECATEDDisabled;
   	private boolean enabledForOthers;
   	private String formattedNumber;
   	private int id;
   	private String incomingAccessNumber;
   	private String name;
   	private String phoneNumber;
   	private int policyBitmask;
   	private boolean redirectToVoicemail;
   	private boolean scheduleSet;
   	private boolean smsEnabled;
   	private boolean telephonyVerified;
   	private int type;
   	private boolean verified;
   	private boolean voicemailForwardingVerified;
   	private Wd wd;
   	private We we;
   	private boolean weekdayAllDay;
   	private String[] weekdayTimes;//Type correct??
   	private boolean weekendAllDay;
   	private String[] weekendTimes;//Type correct??
   	
   	public Phone(int id, String name, String phoneNumber) {
   		this.id = id;
   		this.name = name;
   		this.phoneNumber = phoneNumber;
   	}
   	
   	public Phone(JSONObject phonesJSON) throws JSONException {
   		//TODO implement like Settings.java - FINISH
		if(!saveMode || saveMode && phonesJSON.has("id")) id = phonesJSON.getInt("id");
		if(!saveMode || saveMode && phonesJSON.has("name")) name = phonesJSON.getString("name");
		if(!saveMode || saveMode && phonesJSON.has("phoneNumber")) phoneNumber = phonesJSON.getString("phoneNumber");
   	}
   	
   	public static final Phone[] createArrayFromJsonObject(JSONObject phonesJSON) throws JSONException { 
		String[] phoneNames = JSONObject.getNames(phonesJSON);
		Phone[] result = new Phone[phoneNames.length];
		for (int i = 0; i < phoneNames.length; i++) {
			int lId = phonesJSON.getJSONObject(phoneNames[i]).getInt("id");
			String lName = phonesJSON.getJSONObject(phoneNames[i]).getString("name");
			String lPhoneNumber = phonesJSON.getJSONObject(phoneNames[i]).getString("phoneNumber");
			result[i] = new Phone(lId,lName,lPhoneNumber);
		}
		return result;
	}
   	
   	/**
     * Make a JSON text of the Settings. For compactness, no whitespace
     * is added. If this would not result in a syntactically correct JSON text,
     * then null will be returned instead.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a printable, displayable, portable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     */
	public String toJson(){
		JSONObject resultO = new JSONObject();
		try { 
			JSONObject phoneO = new JSONObject();

			//TODO implement like Settings.java - FINISH
			phoneO.putOnce("id", id);
			phoneO.putOnce("name", name);
			phoneO.putOnce("phoneNumber", phoneNumber);
			
			resultO.put("phones", phoneO);
		} catch (JSONException e) {
			return null;
		}
		
		return resultO.toString();
	}
   	
	
}
