/**
 * 
 */
package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * Holds all settings of the gVoice account and can return it as String, as lists or as json-string
 *
 */
public class Settings {
	private List<Phone> mPhoneList = null;
	private List<VoicemailGreeting> mVoicemailGreetingsList = null;
	private List<GroupSettings> mGroupSettingsList = null;
	
	public Settings(List<Phone> pPhoneList,
			List<VoicemailGreeting> pVoicemailGreetingsList,
			List<GroupSettings> pGroupSettingsList) {
		super();
		mPhoneList = pPhoneList;
		mVoicemailGreetingsList = pVoicemailGreetingsList;
		mGroupSettingsList = pGroupSettingsList;
	}
	
	public Settings(String json) {
		mPhoneList = getPhoneListFromJson(json);
		mVoicemailGreetingsList = getVoicemailGreetingsListFromJson(json);
		mGroupSettingsList = getGroupSettingsListFromJson(json);
	}
	
	public String toJson() {
		String ret="<json><![CDATA[{";

		if(mPhoneList.size()>0) {
			ret+="\"phones\":{";
			for (Iterator<Phone> iterator = mPhoneList.iterator(); iterator.hasNext();) {
				Phone element = (Phone) iterator.next();
				ret+=element.toString(); //TODO change to toJson when implemented
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+=""; //Nothing - error in gvoice json data
		}
		
		if(mVoicemailGreetingsList.size()>0) {
			ret+=",\"greetings\":[";
			for (Iterator<VoicemailGreeting> iterator = mVoicemailGreetingsList.iterator(); iterator.hasNext();) {
				VoicemailGreeting element = (VoicemailGreeting) iterator.next();
				ret+=element.toJson();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="]";
		}
		
		if(mGroupSettingsList.size()>0) {
			ret+=",\"groups\":{";
			for (Iterator<GroupSettings> iterator = mGroupSettingsList.iterator(); iterator.hasNext();) {
				GroupSettings element = (GroupSettings) iterator.next();
				ret+=element.toJson();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="}";
		}
		
		return ret;
	}

	/**
	 * Return the List of GroupSettings from json
	 * @param json
	 * @return
	 */
	private List<GroupSettings> getGroupSettingsListFromJson(String json) {
		List<GroupSettings> result = new ArrayList<GroupSettings>();
		try {
			result = GroupSettings.createGroupSettingsFromJsonResponse(json);
		} catch (Exception e) {
			System.out.println("Error in groupSetting object creation.");
		}
		return result;
	}
	
	/**
	 * Return the List of Voicemail Greetings from json
	 * 
	 */
	private List<VoicemailGreeting> getVoicemailGreetingsListFromJson(String json) {
		List<VoicemailGreeting> result = new ArrayList<VoicemailGreeting>();
		json = ParsingUtil.removeUninterestingParts(json, "\"greetings\":[", "],", false);
		String[] greetingsStrings = json.split(Pattern.quote("},{"));
		// Add System standard greeting
		result.add(new VoicemailGreeting("0", "System Standard"));
		for (int i = 1; i < greetingsStrings.length; i++) {
			String lId =   ParsingUtil.removeUninterestingParts(greetingsStrings[i]  , "\"id\":"  , ",", false);
			String lName = ParsingUtil.removeUninterestingParts(greetingsStrings[i], "\"name\":\"", "\",\"", false);
			VoicemailGreeting lGreeting = new VoicemailGreeting(lId, lName);
			result.add(lGreeting);
		}
		return result;
	}
	
	/**
	 * Return the List of Phones from json
	 */
	private List<Phone> getPhoneListFromJson(String phonesInfoJson) {
		List<Phone> phoneList = new ArrayList<Phone>();
		if (phonesInfoJson!=null) {
			/*
			 * TODO fix
			List<Integer> disabledList = new ArrayList<Integer>();
			
			
			String f1 = phonesInfoJson.substring(phonesInfoJson.indexOf("disabledIdMap\":{"));
			f1=f1.substring(16);
			f1 = f1.substring(0,f1.indexOf("},\""));
			String[] rawslice = f1.split(",");
			
			for(int i=0;i<rawslice.length;i++){
				if(rawslice[i].contains("true")){
					//System.out.println(rawslice[i]);
					rawslice[i] = rawslice[i].replace("\":true", "");
					rawslice[i] =rawslice[i].replace("\"", "");
					//System.out.println(rawslice[i]);
					Integer z = Integer.parseInt(rawslice[i]);
					disabledList.add(z);
				}
			}
			*/
			
		    /* TODO fix
			String[] a = phonesInfoJson.split("\\{\"id\"\\:");
			// if(PRINT_TO_CONSOLE) System.out.println(a[0]);
			for (int i = 1; i < a.length; i++) {
				//Phone phone = new Phone();
				String[] b = a[i].split(",\"wd\"\\:\\{", 2)[0].split(",");
				//phone.id = Integer.parseInt(b[0].replaceAll("\"", ""));
				int id = Integer.parseInt(b[0].replaceAll("\"", ""));
				String number = "";
				String type = "";
				String name = "";
				String formattedNumber="";
				String carrier = "";
				Boolean verified = false;
				for (int j = 0; j < b.length; j++) {
					if (b[j].contains("phoneNumber")) {
						//phone.number = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						number  = b[j].split("\\:")[1]
						   .replaceAll("\"", "");
					} else if (b[j].contains("type")) {
						//phone.type = b[j].split("\\:")[1].replaceAll("\"", "");
						type = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("name")) {
						//phone.name = b[j].split("\\:")[1].replaceAll("\"", "");
						name = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("formattedNumber")) {
						//phone.formattedNumber = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						formattedNumber = b[j].split("\\:")[1]
						    								.replaceAll("\"", "");
					} else if (b[j].contains("carrier")) {
						//phone.carrier = b[j].split("\\:")[1].replaceAll("\"",
						//		"");
						carrier = b[j].split("\\:")[1].replaceAll("\"",
							"");
					} else if (b[j].contains("\"verified")) {
						//phone.verified = Boolean
						//		.parseBoolean(b[j].split("\\:")[1].replaceAll(
						//				"\"", ""));
						verified = Boolean
						.parseBoolean(b[j].split("\\:")[1].replaceAll(
								"\"", ""));
					}
				}
				if(id!=0 && !number.equals("")&&!formattedNumber.equals("")&&!type.equals("")&&!name.equals("")){
					
					boolean enabled;
					
					if(disabledList.contains(new Integer(id))){
						enabled = false;
					}else{
						enabled = true;
					}
					
					Phone phone = new Phone(id, number, formattedNumber, type, name, carrier, verified,enabled);
											
					phoneList.add(phone);
				}
			} */

		} else{
			System.out.println("Error in phone object creation.");
			phoneList = new ArrayList<Phone>();
		}
		return phoneList;
	}

	/**
	 * @return the mPhoneList
	 */
	public List<Phone> getPhoneList() {
		return mPhoneList;
	}

	/**
	 * @return the mVoicemailGreetingsList
	 */
	public List<VoicemailGreeting> getVoicemailGreetingsList() {
		return mVoicemailGreetingsList;
	}

	/**
	 * @return the mGroupSettingsList
	 */
	public List<GroupSettings> getGroupSettingsList() {
		return mGroupSettingsList;
	}
	
	
	
	
}
