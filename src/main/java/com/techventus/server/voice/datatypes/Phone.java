
package com.techventus.server.voice.datatypes;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techventus.server.voice.util.ParsingUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Phone implements Comparable<Phone>{
	Gson gson = new Gson();
   	//TODO - implement

	private boolean saveMode;

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
   	private Wd wd; //TODO
   	private We we; //TODO
   	private boolean weekdayAllDay;
   	private String[] weekdayTimes;
   	private boolean weekendAllDay;
   	private String[] weekendTimes;

   	public Phone(int id, String name, String phoneNumber) {
   		this.id = id;
   		this.name = name;
   		this.phoneNumber = phoneNumber;
   	}

   	/**
	 *
	 * saveMode is off
	 *
	 * @param phonesJSON
	 *
	 */
   	public Phone(JsonObject phonesJSON)  {
   		this(phonesJSON, false);
   	}

   	/**
	    * Instantiates a new Phone Object.
	    *
	    * @param phonesJSON the JSON representation of the Phone object
	    * @param pSaveMode the Save Mode boolean
	    */
   	public Phone(JsonObject phonesJSON, boolean pSaveMode) {
		if(!saveMode || (saveMode && phonesJSON.has("id")) ) id = phonesJSON.get("id").getAsInt();
		if(!saveMode || (saveMode && phonesJSON.has("name")) ) name = phonesJSON.get("name").getAsString();
		if(!saveMode || (saveMode && phonesJSON.has("phoneNumber")) ) phoneNumber = phonesJSON.get("phoneNumber").getAsString();
		if(!saveMode || (saveMode && phonesJSON.has("active")) ) active = phonesJSON.get("active").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("behaviorOnRedirect")) ) behaviorOnRedirect = phonesJSON.get("behaviorOnRedirect").getAsInt();
	   	if(!saveMode || (saveMode && phonesJSON.has("carrier")) ) carrier = phonesJSON.get("carrier").getAsString();
	   	if(!saveMode || (saveMode && phonesJSON.has("customOverrideState")) ) customOverrideState = phonesJSON.get("customOverrideState").getAsInt();
	   	if(!saveMode || (saveMode && phonesJSON.has("dEPRECATEDDisabled")) ) dEPRECATEDDisabled = phonesJSON.get("dEPRECATEDDisabled").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("enabledForOthers")) ) enabledForOthers = phonesJSON.get("enabledForOthers").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("formattedNumber")) ) formattedNumber = phonesJSON.get("formattedNumber").getAsString();
	   	if(!saveMode || (saveMode && phonesJSON.has("incomingAccessNumber")) ) incomingAccessNumber = phonesJSON.get("incomingAccessNumber").getAsString();
	   	if(!saveMode || (saveMode && phonesJSON.has("phoneNumber")) ) phoneNumber = phonesJSON.get("phoneNumber").getAsString();
	   	if(!saveMode || (saveMode && phonesJSON.has("policyBitmask")) ) policyBitmask = phonesJSON.get("policyBitmask").getAsInt();
	   	if(!saveMode || (saveMode && phonesJSON.has("redirectToVoicemail")) ) redirectToVoicemail = phonesJSON.get("redirectToVoicemail").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("scheduleSet")) ) {
		    try {
			    // if not set, this value is "false", but if active it's 1 !! - this is not true... maybe
			    scheduleSet = phonesJSON.get("scheduleSet").getAsBoolean();
		    } catch (Exception jsE) {
			    scheduleSet = false;
		    }
	    }
	   	if(!saveMode || (saveMode && phonesJSON.has("smsEnabled")) ) smsEnabled = phonesJSON.get("smsEnabled").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("telephonyVerified")) ) telephonyVerified = phonesJSON.get("telephonyVerified").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("type")) ) type = phonesJSON.get("type").getAsInt();
	   	if(!saveMode || (saveMode && phonesJSON.has("verified")) ) verified = phonesJSON.get("verified").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("voicemailForwardingVerified")) ) voicemailForwardingVerified = phonesJSON.get("voicemailForwardingVerified").getAsBoolean();
//	   	if(!saveMode || (saveMode && phonesJSON.has("wd")) ) wd = phonesJSON.getInt("id"); //TODO
//	   	if(!saveMode || (saveMode && phonesJSON.has("we")) ) wd = phonesJSON.getInt("id"); //TODO
	   	if(!saveMode || (saveMode && phonesJSON.has("weekdayAllDay")) ) weekdayAllDay = phonesJSON.get("weekdayAllDay").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("weekdayTimes")) ) weekdayTimes = ParsingUtil.jsonStringArrayToStringArray(phonesJSON.get("weekdayTimes").getAsJsonArray());//Type correct??
	   	if(!saveMode || (saveMode && phonesJSON.has("weekendAllDay")) ) weekendAllDay = phonesJSON.get("weekendAllDay").getAsBoolean();
	   	if(!saveMode || (saveMode && phonesJSON.has("weekendTimes")) ) weekendTimes = ParsingUtil.jsonStringArrayToStringArray(phonesJSON.get("weekendTimes").getAsJsonArray());//Type correct??
   	}

   	public static final Phone[] createArrayFromJsonObject(JsonObject phonesJSON) throws IllegalStateException {
	    Set<Map.Entry<String, JsonElement>> set = phonesJSON.entrySet();
	    Phone[] result = new Phone[set.size()];
	    int i = 0 ;
	    for(Map.Entry<String, JsonElement> entry : set) {
		    entry.getKey();
		    result[i] = new Phone(phonesJSON.get(entry.getKey()).getAsJsonObject());
		    i++;
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
		return gson.toJson(this);
	}

	public JsonObject toJsonObject(){
		return gson.toJsonTree(this).getAsJsonObject();
	}

	/**
	 * @param phones
	 * @return Json Object
	 */
	public static Object phonesArrayToJsonObject(Phone[] phones) {
		Map<String, Phone> map = new HashMap<>();
		Gson lgson = new Gson();

		for (Phone phone : phones) {
			map.put(String.valueOf(phone.getId()), phone);
		}
		return lgson.toJsonTree(map).getAsJsonObject();
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
	 * returns the value for smsEnabled for this phone
	 * @return
	 */
	public boolean getSmsEnabled() {
		return smsEnabled;
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



	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @param behaviorOnRedirect the behaviorOnRedirect to set
	 */
	public void setBehaviorOnRedirect(int behaviorOnRedirect) {
		this.behaviorOnRedirect = behaviorOnRedirect;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @param customOverrideState the customOverrideState to set
	 */
	public void setCustomOverrideState(int customOverrideState) {
		this.customOverrideState = customOverrideState;
	}

	/**
	 * @param dEPRECATEDDisabled the dEPRECATEDDisabled to set
	 */
	public void setdEPRECATEDDisabled(boolean dEPRECATEDDisabled) {
		this.dEPRECATEDDisabled = dEPRECATEDDisabled;
	}

	/**
	 * @param enabledForOthers the enabledForOthers to set
	 */
	public void setEnabledForOthers(boolean enabledForOthers) {
		this.enabledForOthers = enabledForOthers;
	}

	/**
	 * @param formattedNumber the formattedNumber to set
	 */
	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param incomingAccessNumber the incomingAccessNumber to set
	 */
	public void setIncomingAccessNumber(String incomingAccessNumber) {
		this.incomingAccessNumber = incomingAccessNumber;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @param policyBitmask the policyBitmask to set
	 */
	public void setPolicyBitmask(int policyBitmask) {
		this.policyBitmask = policyBitmask;
	}

	/**
	 * @param redirectToVoicemail the redirectToVoicemail to set
	 */
	public void setRedirectToVoicemail(boolean redirectToVoicemail) {
		this.redirectToVoicemail = redirectToVoicemail;
	}

	/**
	 * @param scheduleSet the scheduleSet to set
	 */
	public void setScheduleSet(boolean scheduleSet) {
		this.scheduleSet = scheduleSet;
	}

	/**
	 * @param smsEnabled the smsEnabled to set
	 */
	public void setSmsEnabled(boolean smsEnabled) {
		this.smsEnabled = smsEnabled;
	}

	/**
	 * @param telephonyVerified the telephonyVerified to set
	 */
	public void setTelephonyVerified(boolean telephonyVerified) {
		this.telephonyVerified = telephonyVerified;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @param voicemailForwardingVerified the voicemailForwardingVerified to set
	 */
	public void setVoicemailForwardingVerified(boolean voicemailForwardingVerified) {
		this.voicemailForwardingVerified = voicemailForwardingVerified;
	}

	/**
	 * @param wd the wd to set
	 */
	public void setWd(Wd wd) {
		this.wd = wd;
	}

	/**
	 * @param we the we to set
	 */
	public void setWe(We we) {
		this.we = we;
	}

	/**
	 * @param weekdayAllDay the weekdayAllDay to set
	 */
	public void setWeekdayAllDay(boolean weekdayAllDay) {
		this.weekdayAllDay = weekdayAllDay;
	}

	/**
	 * @param weekdayTimes the weekdayTimes to set
	 */
	public void setWeekdayTimes(String[] weekdayTimes) {
		this.weekdayTimes = weekdayTimes;
	}

	/**
	 * @param weekendAllDay the weekendAllDay to set
	 */
	public void setWeekendAllDay(boolean weekendAllDay) {
		this.weekendAllDay = weekendAllDay;
	}

	/**
	 * @param weekendTimes the weekendTimes to set
	 */
	public void setWeekendTimes(String[] weekendTimes) {
		this.weekendTimes = weekendTimes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Phone o) {
		if( id < o.getId() )
            return -1;
        if( id > o.getId() )
            return 1;

        return 0;
	}


}
