package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techventus.server.voice.util.ParsingUtil;
import org.apache.commons.lang.NotImplementedException;

/**
 *
 *
 * @author Tobias Eisentraeger
 *
 */
public class Group {
	private Gson gson = new Gson();

	private boolean saveMode = true;
	private String id;
	private String name;
	private boolean isCustomForwarding;
	private List<DisabledForwardingId> disabledForwardingIds;
	private DisabledForwardingId[] disabledForwardingIdsNeu;
	private boolean isCustomDirectConnect;
	private boolean directConnect;
	private boolean isCustomGreeting;
	private int greetingId;

	public Group(String id, String name, boolean isCustomForwarding,
			List<DisabledForwardingId> disabledForwardingIds,
			boolean isCustomDirectConnect, boolean directConnect,
			boolean isCustomGreeting, int greetingId) {
		super();
		this.id = id;
		this.name = name;
		this.isCustomForwarding = isCustomForwarding;
		this.disabledForwardingIds = disabledForwardingIds;
		this.isCustomDirectConnect = isCustomDirectConnect;
		this.directConnect = directConnect;
		this.isCustomGreeting = isCustomGreeting;
		this.greetingId = greetingId;
	}

	public Group(String id, String name, boolean isCustomForwarding,
			DisabledForwardingId[] disabledForwardingIdsNeu,
			boolean isCustomDirectConnect, boolean directConnect,
			boolean isCustomGreeting, int greetingId) {
		super();
		this.id = id;
		this.name = name;
		this.isCustomForwarding = isCustomForwarding;
		this.disabledForwardingIdsNeu = disabledForwardingIdsNeu;
		this.isCustomDirectConnect = isCustomDirectConnect;
		this.directConnect = directConnect;
		this.isCustomGreeting = isCustomGreeting;
		this.greetingId = greetingId;
	}

	/**
	 * @param jsonObject
	 */
	public Group(JsonObject jsonObject)  {
		if(!saveMode || saveMode && jsonObject.has("id")) id = jsonObject.get("id").getAsString();
		if(!saveMode || saveMode && jsonObject.has("name")) name = jsonObject.get("name").getAsString();
		if(!saveMode || saveMode && jsonObject.has("isCustomForwarding")) isCustomForwarding = jsonObject.get("isCustomForwarding").getAsBoolean();
		if(!saveMode || saveMode && jsonObject.has("isCustomGreeting")) isCustomGreeting = jsonObject.get("isCustomGreeting").getAsBoolean();
		if(!saveMode || saveMode && jsonObject.has("isCustomDirectConnect")) isCustomDirectConnect = jsonObject.get("isCustomDirectConnect").getAsBoolean();
		if(!saveMode || saveMode && jsonObject.has("directConnect")) directConnect = jsonObject.get("directConnect").getAsBoolean();
		if(!saveMode || saveMode && jsonObject.has("greetingId")) greetingId = jsonObject.get("greetingId").getAsInt();
		if(!saveMode || saveMode && jsonObject.has("disabledForwardingIds")) {
			JsonElement disabledForwardingIdsObject = jsonObject.get("disabledForwardingIds");
			disabledForwardingIds = DisabledForwardingId.createDisabledForwardingIdListFromJsonPartResponse(disabledForwardingIdsObject.toString());
		}
	}

	/**
	 * Constructs an Object from the json Resonse
	 * @param json
	 */
	public final static List<Group> createGroupSettingsFromJsonResponse(String json) {
		List<Group> result = new ArrayList<Group>();

		json = ParsingUtil.removeUninterestingParts(json, "\"groups\":{", ",\"groupList\"", false);
		json = json.replaceAll("\\},\"isCustomForwarding\"", "!,\"isCustomForwarding\"");

		String[] groupsStrings = json.split(Pattern.quote("},"));

		for (int i = 0; i < groupsStrings.length; i++) {
			String id 						= ParsingUtil.removeUninterestingParts(groupsStrings[i]  , "\"id\":\""  , "\"", false);
			String name 					= ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"name\":\"", "\",\"", false);
			boolean isCustomForwarding 		= Boolean.parseBoolean(ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"isCustomForwarding\":", ",", false));
			boolean isCustomGreeting 		= Boolean.parseBoolean(ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"isCustomGreeting\":", ",", false));
			boolean isCustomDirectConnect 	= Boolean.parseBoolean(ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"isCustomDirectConnect\":", ",", false));
			boolean directConnect 			= Boolean.parseBoolean(ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"directConnect\":", ",", false));
			int greetingId 					= Integer.parseInt(ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"greetingId\":", ",", false));
			String disabledForwardingIdsStr	= ParsingUtil.removeUninterestingParts(groupsStrings[i], "\"disabledForwardingIds\":{", "!,\"", false);

			List<DisabledForwardingId> disabledForwardingIds = new ArrayList<DisabledForwardingId>();
			if(disabledForwardingIdsStr!=null &! disabledForwardingIdsStr.equals("")) {
				disabledForwardingIds = DisabledForwardingId.createDisabledForwardingIdListFromJsonPartResponse(disabledForwardingIdsStr);
			}

			result.add(new Group(id, name, isCustomForwarding, disabledForwardingIds, isCustomDirectConnect, directConnect, isCustomGreeting, greetingId));
		}

		return result;

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String ret="{id="+id+";";
		ret+="name="+name+";";
		ret+="isCustomDirectConnect="+isCustomDirectConnect+";";
		ret+="directConnect="+directConnect+";";
		ret+="isCustomGreeting="+isCustomGreeting+";";
		ret+="isCustomForwarding="+isCustomForwarding+";";
		ret+="greetingId="+greetingId+";";
		ret+="disabledForwardingIds="+disabledForwardingIds+"}";
		return ret;
	}
	public String toJson(){
		return gson.toJson(this);
	}

	public JsonObject toJsonObject(){
		return gson.toJsonTree(this).getAsJsonObject();
	}
	/*
	public String toJson(){
		String ret = "\""+id+"\":{";
		ret+="\"id\":\""+id+"\",";
		ret+="\"name\":\""+name+"\",";
		ret+="\"disabledForwardingIds\":{";
		for (Iterator<DisabledForwardingId> iterator = disabledForwardingIds.iterator(); iterator.hasNext();) {
			DisabledForwardingId element = (DisabledForwardingId) iterator.next();
			ret+=element.toJson();
			if(iterator.hasNext()) {
				ret+=",";
			}
		}
		ret+="},";
		ret+="\"isCustomForwarding\":"+isCustomForwarding+",";
		ret+="\"isCustomGreeting\":"+isCustomGreeting+",";
		ret+="\"isCustomDirectConnect\":"+isCustomDirectConnect+",";
		ret+="\"directConnect\":"+directConnect+",";
		ret+="\"greetingId\":"+greetingId+"}";
		return ret;
	}
	*/
	/**
	 * Creates a complete json of a list of Group
	 * "groups":{"15":{..details of group id 15..},"12":{..details of group id 12..}}
	 *
	 * @param pGroupSettings the group settings
	 * @return the JSON string
	 */
	public static String listToJson(List<Group> pGroupSettings) {
		String ret = "\"groups\":{";
		for (Iterator<Group> iterator = pGroupSettings.iterator(); iterator.hasNext();) {
			Group setting = (Group) iterator.next();
			String id = ParsingUtil.removeUninterestingParts(setting.toString(), "{id=", ";name", false);
			ret+= "\"" + id + "\":" + setting.toJson();
			if(iterator.hasNext()) {
				ret+=",";
			}
		}
		ret+="}";
		return ret;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the isCustomForwarding - Call Presentation
	 */
	public boolean isCustomForwarding() {
		return isCustomForwarding;
	}

	/**
	 * @return the disabledForwardingIds
	 */
	public List<DisabledForwardingId> getDisabledForwardingIds() {
		return disabledForwardingIds;
	}

	/**
	 * @return the isCustomDirectConnect
	 */
	public boolean isCustomDirectConnect() {
		return isCustomDirectConnect;
	}

	/**
	 * @return the directConnect
	 */
	public boolean isDirectConnect() {
		return directConnect;
	}

	/**
	 * @return the isCustomGreeting
	 */
	public boolean isCustomGreeting() {
		return isCustomGreeting;
	}



	/**
	 * @return the greetingId
	 */
	public int getGreetingId() {
		return greetingId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param isCustomForwarding the isCustomForwarding to set
	 */
	public void setCustomForwarding(boolean isCustomForwarding) {
		this.isCustomForwarding = isCustomForwarding;
	}

	/**
	 * @param disabledForwardingIds the disabledForwardingIds to set
	 */
	public void setDisabledForwardingIds(
			List<DisabledForwardingId> disabledForwardingIds) {
		this.disabledForwardingIds = disabledForwardingIds;
	}

	/**
	 * @param isCustomDirectConnect the isCustomDirectConnect to set
	 */
	public void setCustomDirectConnect(boolean isCustomDirectConnect) {
		this.isCustomDirectConnect = isCustomDirectConnect;
	}

	/**
	 * @param directConnect the directConnect to set
	 */
	public void setDirectConnect(boolean directConnect) {
		this.directConnect = directConnect;
	}

	/**
	 * @param isCustomGreeting the isCustomGreeting to set
	 */
	public void setCustomGreeting(boolean isCustomGreeting) {
		this.isCustomGreeting = isCustomGreeting;
	}

	/**
	 * @param greetingId the greetingId to set
	 */
	public void setGreetingId(int greetingId) {
		this.greetingId = greetingId;
	}


	public static Group[] createArrayFromJsonObject(JsonElement groupsJSON) {
		List<String> names = ParsingUtil.names(groupsJSON.getAsJsonObject());
		List<Group> groups = Lists.newArrayList();

		for(String name : names) {
			JsonElement element = groupsJSON.getAsJsonObject().get(name);
			groups.add(new Group(element.getAsJsonObject()));

		}
		return groups.toArray(new Group[0]);

	}

	public final static JsonObject[] createJSONObjectArrayFromJsonObject(JsonObject settingsJSON)  {
//		Group[] lGroupsArray = Group.createArrayFromJsonObject(settingsJSON);
//		JsonObject[] result = new JsonObject[lGroupsArray.length];
//		for (int i = 0; i < lGroupsArray.length; i++) {
//			result[i] = new JsonObject();
//			result[i].put(lGroupsArray[i].getId(), lGroupsArray[i]);
//		}
//		return result;
		throw new NotImplementedException();
	}

	public final static JsonObject createJSONObjectFromJsonObject(JsonObject settingsJSON) {
//		Group[] lGroupsArray = Group.createArrayFromJsonObject(settingsJSON);
//		JSONObject result = new JSONObject();
//		for (int i = 0; i < lGroupsArray.length; i++) {
//			result = new JSONObject();
//			result.put(lGroupsArray[i].getId(), lGroupsArray[i]);
//		}
//		return result;
		throw new NotImplementedException();
	}

	/**
	 * Groups array to json object.
	 *
	 * @param groups the groups
	 * @return the object
	 */
	public static JsonObject groupsArrayToJsonObject(Group[] groups) {
		Gson gson = new Gson();
		return gson.toJsonTree(groups).getAsJsonObject();
	}

	/**
	 * Query disabled status - if id not found, then it returns false, which means enabled.
	 *
	 * @param phoneId the phone id
	 * @return true, if is phone disabled
	 */
   	public boolean isPhoneDisabled(int phoneId) {
   		boolean ret = false;
   		try {
 			if(disabledForwardingIds!=null) {
	   			for (int i = 0; i < disabledForwardingIds.size(); i++) {
	 				if(disabledForwardingIds.get(i).getId().equals(phoneId+"")) {
	 					ret = true;
	 				}
				}
 			} else {
 				// list is null, so we return false
 				ret = false;
 			}
 		} catch (NullPointerException e) {
 			ret = false;
 		}
 		return ret;
   	}

}
