package com.techventus.server.voice.datatypes;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techventus.server.voice.util.ParsingUtil;

public class Setting {
	
	private final static boolean saveMode = true;

	// Settings in order of the json
	private int[] mActiveForwardingList;
	private String baseUrl;
	private int credits;
	private int defaultGreetingId;
	private String[] mDidInfos;
    private boolean directConnect;
    private DisabledId[] mDisabledIdList;
    private boolean doNotDisturb;
    private EmailAddress[] emailAddresses;
    private boolean emailNotificationActive;
    private String emailNotificationAddress;
    private Greeting[] greetings;
    private String[] groupList;
    private JSONObject groups;
	private String language;
	private String primaryDid;
	private int screenBehavior;
	private boolean showTranscripts;
	private String[] smsNotifications;
	private boolean smsToEmailActive;
	private boolean smsToEmailSubject;
	private int spam;
	private String timezone;
	private boolean useDidAsCallerId;
	private boolean useDidAsSource;
	
	public Setting(JSONObject settingsJSON) throws JSONException {
		if(!saveMode || saveMode && settingsJSON.has("activeForwardingIds")) mActiveForwardingList = ParsingUtil.jsonIntArrayToIntArray(settingsJSON.getJSONArray("activeForwardingIds"));
		if(!saveMode || saveMode && settingsJSON.has("baseUrl")) baseUrl = settingsJSON.getString("baseUrl");
		if(!saveMode || saveMode && settingsJSON.has("credits")) credits = settingsJSON.getInt("credits");
		if(!saveMode || saveMode && settingsJSON.has("defaultGreetingId")) defaultGreetingId = settingsJSON.getInt("defaultGreetingId");
		if(!saveMode || saveMode && settingsJSON.has("didInfos")) mDidInfos = ParsingUtil.jsonStringArrayToStringArray(settingsJSON.getJSONArray("didInfos"));
		if(!saveMode || saveMode && settingsJSON.has("directConnect")) directConnect =  settingsJSON.getBoolean("directConnect");
		if(!saveMode || saveMode && settingsJSON.has("disabledIdMap")) mDisabledIdList = DisabledId.createArrayFromJsonObject(settingsJSON);
		if(!saveMode || saveMode && settingsJSON.has("doNotDisturb")) doNotDisturb =  settingsJSON.getBoolean("doNotDisturb");
		if(!saveMode || saveMode && settingsJSON.has("emailAddresses")) emailAddresses = EmailAddress.createArrayFromJsonObject(settingsJSON);
		if(!saveMode || saveMode && settingsJSON.has("emailNotificationActive")) emailNotificationActive =  settingsJSON.getBoolean("emailNotificationActive");
		if(!saveMode || saveMode && settingsJSON.has("emailNotificationAddress")) emailNotificationAddress = settingsJSON.getString("emailNotificationAddress");
		if(!saveMode || saveMode && settingsJSON.has("greetings")) greetings = Greeting.createArrayFromJsonObject(settingsJSON);
		if(!saveMode || saveMode && settingsJSON.has("groupList")) groupList = ParsingUtil.jsonStringArrayToStringArray(settingsJSON.getJSONArray("groupList"));
		if(!saveMode || saveMode && settingsJSON.has("groups")) groups = settingsJSON.getJSONObject("groups");
		if(!saveMode || saveMode && settingsJSON.has("language")) language = settingsJSON.getString("language");
		if(!saveMode || saveMode && settingsJSON.has("primaryDid")) primaryDid = settingsJSON.getString("primaryDid");
		if(!saveMode || saveMode && settingsJSON.has("screenBehavior")) screenBehavior = settingsJSON.getInt("screenBehavior");
		if(!saveMode || saveMode && settingsJSON.has("showTranscripts")) showTranscripts = settingsJSON.getBoolean("showTranscripts");
		if(!saveMode || saveMode && settingsJSON.has("smsNotifications")) smsNotifications = ParsingUtil.jsonStringArrayToStringArray(settingsJSON.getJSONArray("smsNotifications"));
		if(!saveMode || saveMode && settingsJSON.has("smsToEmailActive")) smsToEmailActive =  settingsJSON.getBoolean("smsToEmailActive");
		if(!saveMode || saveMode && settingsJSON.has("smsToEmailSubject")) smsToEmailSubject = settingsJSON.getBoolean("smsToEmailSubject");
		if(!saveMode || saveMode && settingsJSON.has("spam")) spam = settingsJSON.getInt("spam");
		if(!saveMode || saveMode && settingsJSON.has("timezone")) timezone = settingsJSON.getString("timezone");
		if(!saveMode || saveMode && settingsJSON.has("useDidAsCallerId")) useDidAsCallerId = settingsJSON.getBoolean("useDidAsCallerId");
		if(!saveMode || saveMode && settingsJSON.has("useDidAsSource")) useDidAsSource = settingsJSON.getBoolean("useDidAsSource");
		//TODO webCallButtons
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
		JSONObject settingsO = new JSONObject();
		try { 
			//JSONObject settingsO = new JSONObject();

			settingsO.putOpt("activeForwardingIds", mActiveForwardingList);
			settingsO.putOpt("baseUrl", baseUrl);
			settingsO.putOpt("credits", credits);
			settingsO.putOpt("defaultGreetingId", defaultGreetingId);
			settingsO.putOpt("didInfos", mDidInfos);
			settingsO.putOpt("directConnect", directConnect);
			for (int i = 0; i < mDisabledIdList.length; i++) {
				settingsO.accumulate("disabledIdMap", mDisabledIdList[i].toJsonObject());
			}
			settingsO.putOpt("doNotDisturb", doNotDisturb);
			for (int i = 0; i < emailAddresses.length; i++) {
				settingsO.append("emailAddresses", emailAddresses[i].getAddress());
			}
			settingsO.putOpt("emailNotificationActive", emailNotificationActive);
			settingsO.putOpt("emailNotificationAddress", emailNotificationAddress);
			settingsO.putOpt("greetings", greetings); // An Object Array uses the Bean get Methods - no toJson() needed in Greeting
			settingsO.putOpt("groupList", groupList);
			
			JSONObject groupObject = new JSONObject();
			String[] groupNames = JSONObject.getNames(groups);
			if(groupNames!=null && groupNames.length>0) {
				for (int i = 0; i < groupNames.length; i++) {
					JSONObject oneGroupObject = groups;
					groupObject.putOpt(groupNames[i], oneGroupObject);
				}
			}
			settingsO.putOpt("groups", groups);
			
			settingsO.putOpt("language", language);
			settingsO.putOpt("primaryDid", primaryDid);
			settingsO.putOpt("screenBehavior", screenBehavior);
			settingsO.putOpt("showTranscripts", showTranscripts);
			settingsO.putOpt("smsNotifications", smsNotifications);
			settingsO.putOpt("smsToEmailActive", smsToEmailActive);
			settingsO.putOpt("smsToEmailSubject", smsToEmailSubject);
			settingsO.putOpt("spam", spam);
			settingsO.putOpt("timezone", timezone);
			settingsO.putOpt("useDidAsCallerId", useDidAsCallerId);
			settingsO.putOpt("useDidAsSource", useDidAsSource);
			
			//resultO.put("settings", settingsO);
		} catch (JSONException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
		return settingsO;
	}

	/**
	 * @return the mActiveForwardingList
	 */
	public int[] getmActiveForwardingList() {
		return mActiveForwardingList;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * @return the defaultGreetingId
	 */
	public int getDefaultGreetingId() {
		return defaultGreetingId;
	}

	/**
	 * @return the mDidInfos
	 */
	public String[] getmDidInfos() {
		return mDidInfos;
	}

	/**
	 * @return the directConnect
	 */
	public boolean isDirectConnect() {
		return directConnect;
	}

	/**
	 * @return the mDisabledIdList
	 */
	public DisabledId[] getmDisabledIdList() {
		return mDisabledIdList;
	}

	/**
	 * @return the doNotDisturb
	 */
	public boolean isDoNotDisturb() {
		return doNotDisturb;
	}

	/**
	 * @return the emailAddresses
	 */
	public EmailAddress[] getEmailAddresses() {
		return emailAddresses;
	}

	/**
	 * @return the emailNotificationActive
	 */
	public boolean isEmailNotificationActive() {
		return emailNotificationActive;
	}

	/**
	 * @return the emailNotificationAddress
	 */
	public String getEmailNotificationAddress() {
		return emailNotificationAddress;
	}

	/**
	 * @return the greetings
	 */
	public Greeting[] getGreetings() {
		return greetings;
	}

	/**
	 * @return the groupList
	 */
	public String[] getGroupList() {
		return groupList;
	}

	/**
	 * @return the groups
	 */
	public JSONObject getGroups() {
		return groups;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the primaryDid
	 */
	public String getPrimaryDid() {
		return primaryDid;
	}

	/**
	 * @return the screenBehavior
	 */
	public int getScreenBehavior() {
		return screenBehavior;
	}

	/**
	 * @return the showTranscripts
	 */
	public boolean isShowTranscripts() {
		return showTranscripts;
	}

	/**
	 * @return the smsNotifications
	 */
	public String[] getSmsNotifications() {
		return smsNotifications;
	}

	/**
	 * @return the smsToEmailActive
	 */
	public boolean isSmsToEmailActive() {
		return smsToEmailActive;
	}

	/**
	 * @return the smsToEmailSubject
	 */
	public boolean isSmsToEmailSubject() {
		return smsToEmailSubject;
	}

	/**
	 * @return the spam
	 */
	public int getSpam() {
		return spam;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @return the useDidAsCallerId
	 */
	public boolean isUseDidAsCallerId() {
		return useDidAsCallerId;
	}

	/**
	 * @return the useDidAsSource
	 */
	public boolean isUseDidAsSource() {
		return useDidAsSource;
	}
	
	

}
