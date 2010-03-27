package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONException;
import gvjava.org.json.JSONObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * 
 * 
 * @author Tobias Eisentraeger
 *
 */
public class Group {
	
	private boolean saveMode = true;
	private String id;
	private String name;
	private boolean isCustomForwarding;
	private List<DisabledForwardingId> disabledForwardingIds;
	private DisabledForwardingId[] disabledForwardingIdsNeu;
	private boolean isCustomDirectConnect;
	private boolean directConnect;
	private boolean isCustomGreeting;
	private String greetingId;
	
	public Group(String id, String name, boolean isCustomForwarding,
			List<DisabledForwardingId> disabledForwardingIds,
			boolean isCustomDirectConnect, boolean directConnect,
			boolean isCustomGreeting, String greetingId) {
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
			boolean isCustomGreeting, String greetingId) {
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
	 * @throws JSONException 
	 */
	public Group(JSONObject jsonObject) throws JSONException {
		if(!saveMode || saveMode && jsonObject.has("id")) id = jsonObject.getString("id");
		if(!saveMode || saveMode && jsonObject.has("name")) name = jsonObject.getString("name");
		if(!saveMode || saveMode && jsonObject.has("isCustomForwarding")) isCustomForwarding = jsonObject.getBoolean("isCustomForwarding");
		if(!saveMode || saveMode && jsonObject.has("isCustomGreeting")) isCustomGreeting = jsonObject.getBoolean("isCustomGreeting");
		if(!saveMode || saveMode && jsonObject.has("isCustomDirectConnect")) isCustomDirectConnect = jsonObject.getBoolean("isCustomDirectConnect");
		if(!saveMode || saveMode && jsonObject.has("directConnect")) directConnect = jsonObject.getBoolean("directConnect");
		if(!saveMode || saveMode && jsonObject.has("greetingId")) greetingId = jsonObject.getString("greetingId");
		if(!saveMode || saveMode && jsonObject.has("disabledForwardingIds")) {
			JSONObject disabledForwardingIdsObject = jsonObject.getJSONObject("disabledForwardingIds");
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
			String greetingId 				= groupsStrings[i].substring(groupsStrings[i].indexOf("\"greetingId\":")+13);
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
		ret+="greetingId="+greetingId+";";
		ret+="isCustomForwarding="+isCustomForwarding+";";
		ret+="disabledForwardingIds="+disabledForwardingIds+"}";
		return ret;
	}
	public String toJson(){	
		return toJsonObject().toString();
	}
	
	public JSONObject toJsonObject(){
		JSONObject resultO = new JSONObject();
		try { 		
			resultO.putOpt("id", id);
			resultO.putOpt("name", name);
			resultO.putOpt("isCustomForwarding", isCustomForwarding);
		   	resultO.accumulate("disabledForwardingIds", DisabledForwardingId.arrayToJsonObject(disabledForwardingIds)); 
		   	resultO.putOpt("isCustomDirectConnect", isCustomDirectConnect);
		   	resultO.putOpt("directConnect", directConnect);
		   	resultO.putOpt("isCustomGreeting", isCustomGreeting);
		   	resultO.putOpt("greetingId", greetingId);
		} catch (JSONException e) {
			return null;
		}
		
		return resultO;
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
	 "groups":{"15":{..details of group id 15..},"12":{..details of group id 12..}}
	 * @return
	 */
	public static String listToJson(List<Group> pGroupSettings) {
		String ret = "\"groups\":{";
		for (Iterator<Group> iterator = pGroupSettings.iterator(); iterator.hasNext();) {
			Group setting = (Group) iterator.next();
			ret+=setting.toJson();
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
	public String getGreetingId() {
		return greetingId;
	}

	//TODO dotn create list first, direct transform
	public final static Group[] createArrayFromJsonObject(JSONObject groupsJSON) throws JSONException { 
		JSONArray groupNames = groupsJSON.names();
		Group[] result = new Group[groupsJSON.length()];
		for (int i = 0; i < groupNames.length(); i++) {
			result[i] = new Group(groupsJSON.getJSONObject(groupNames.getString(i)));
		}
		return result;
		
		/*
		JSONObject lGroupsO = settingsJSON.getJSONObject("groups");
		JSONArray lgroupNames = lGroupsO.names();
		Group[] result = new Group[lgroupNames.length()];
		
		for (int i = 0; i < lgroupNames.length(); i++) {
			String id 						= lGroupsO.getJSONObject(lgroupNames.getString(i)).getString("id");
			String name 					= lGroupsO.getJSONObject(lgroupNames.getString(i)).getString("name");
			boolean isCustomForwarding 		= lGroupsO.getJSONObject(lgroupNames.getString(i)).getBoolean("isCustomForwarding");
			DisabledForwardingId[] disabledForwardingIdsNeu = new DisabledForwardingId[0];
			if(isCustomForwarding) {
				// "disabledForwardingIds":{"1":true}
				JSONObject lCustomForwardingIds  = lGroupsO.getJSONObject(lgroupNames.getString(i)).getJSONObject("disabledForwardingIds");
				String[] lforwardingNames = JSONObject.getNames(lCustomForwardingIds);
				disabledForwardingIdsNeu = new DisabledForwardingId[lforwardingNames.length];
				for (int j = 0; j < lforwardingNames.length; j++) {
					String lFwdId = lforwardingNames[j];
					boolean lFwdState = lCustomForwardingIds.getBoolean(lFwdId);
					disabledForwardingIdsNeu[j] = new DisabledForwardingId(lFwdId,lFwdState);
				}
			}
			boolean isCustomGreeting 		= lGroupsO.getJSONObject(lgroupNames.getString(i)).getBoolean("isCustomGreeting");
			boolean isCustomDirectConnect 	= lGroupsO.getJSONObject(lgroupNames.getString(i)).getBoolean("isCustomDirectConnect");
			boolean directConnect 			= lGroupsO.getJSONObject(lgroupNames.getString(i)).getBoolean("directConnect");
			String greetingId 				= lGroupsO.getJSONObject(lgroupNames.getString(i)).getString("greetingId");
			//?? String disabledForwardingIdsStr	= lGroupsO.getJSONObject(lgroupNames.getString(i)).getString("disabledForwardingIds");
			result[i] = new Group(id, name, isCustomForwarding, disabledForwardingIdsNeu, isCustomDirectConnect, directConnect, isCustomGreeting, greetingId);
		}
		
		return result;
		*/
	}
	
	public final static JSONObject[] createJSONObjectArrayFromJsonObject(JSONObject settingsJSON) throws JSONException { 
		Group[] lGroupsArray = Group.createArrayFromJsonObject(settingsJSON);
		JSONObject[] result = new JSONObject[lGroupsArray.length];
		for (int i = 0; i < lGroupsArray.length; i++) {
			result[i] = new JSONObject();
			result[i].put(lGroupsArray[i].getId(), lGroupsArray[i]);
		}
		return result;
	}
	
	public final static JSONObject createJSONObjectFromJsonObject(JSONObject settingsJSON) throws JSONException { 
		Group[] lGroupsArray = Group.createArrayFromJsonObject(settingsJSON);
		JSONObject result = new JSONObject();
		for (int i = 0; i < lGroupsArray.length; i++) {
			result = new JSONObject();
			result.put(lGroupsArray[i].getId(), lGroupsArray[i]);
		}
		return result;
	}

	/**
	 * @return
	 * @throws JSONException 
	 */
	public static Object phonesArrayToJsonObject(Group[] groups) throws JSONException {
		JSONObject groupO = new JSONObject();
		for (int i = 0; i < groups.length; i++) {
			groupO.put(groups[i].getId()+"",groups[i].toJsonObject());
		}
		return groupO;
	}
	
	


	
}
