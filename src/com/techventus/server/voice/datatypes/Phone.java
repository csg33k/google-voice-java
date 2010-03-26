
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
		return toJsonObject().toString();
	}
	
	public JSONObject toJsonObject(){
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
		
		return resultO;
	}

	/**
	 * @param phones
	 * @return
	 * @throws JSONException 
	 */
	public static Object phonesArrayToJsonObject(Phone[] phones) throws JSONException {
		JSONObject phoneO = new JSONObject();
		for (int i = 0; i < phones.length; i++) {
			phoneO.putOnce(phones[i].getName(),phones[i].toJsonObject());
		}
		return phoneO;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the behaviorOnRedirect
	 */
	public int getBehaviorOnRedirect() {
		return behaviorOnRedirect;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @return the customOverrideState
	 */
	public int getCustomOverrideState() {
		return customOverrideState;
	}

	/**
	 * @return the dEPRECATEDDisabled
	 */
	public boolean isdEPRECATEDDisabled() {
		return dEPRECATEDDisabled;
	}

	/**
	 * @return the enabledForOthers
	 */
	public boolean isEnabledForOthers() {
		return enabledForOthers;
	}

	/**
	 * @return the formattedNumber
	 */
	public String getFormattedNumber() {
		return formattedNumber;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the incomingAccessNumber
	 */
	public String getIncomingAccessNumber() {
		return incomingAccessNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the policyBitmask
	 */
	public int getPolicyBitmask() {
		return policyBitmask;
	}

	/**
	 * @return the redirectToVoicemail
	 */
	public boolean isRedirectToVoicemail() {
		return redirectToVoicemail;
	}

	/**
	 * @return the scheduleSet
	 */
	public boolean isScheduleSet() {
		return scheduleSet;
	}

	/**
	 * @return the smsEnabled
	 */
	public boolean isSmsEnabled() {
		return smsEnabled;
	}

	/**
	 * @return the telephonyVerified
	 */
	public boolean isTelephonyVerified() {
		return telephonyVerified;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @return the voicemailForwardingVerified
	 */
	public boolean isVoicemailForwardingVerified() {
		return voicemailForwardingVerified;
	}

	/**
	 * @return the wd
	 */
	public Wd getWd() {
		return wd;
	}

	/**
	 * @return the we
	 */
	public We getWe() {
		return we;
	}

	/**
	 * @return the weekdayAllDay
	 */
	public boolean isWeekdayAllDay() {
		return weekdayAllDay;
	}

	/**
	 * @return the weekdayTimes
	 */
	public String[] getWeekdayTimes() {
		return weekdayTimes;
	}

	/**
	 * @return the weekendAllDay
	 */
	public boolean isWeekendAllDay() {
		return weekendAllDay;
	}

	/**
	 * @return the weekendTimes
	 */
	public String[] getWeekendTimes() {
		return weekendTimes;
	}
   	
	
}
