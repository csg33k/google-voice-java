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
	private List<Greeting> mVoicemailGreetingsList = null;
	private List<Group> mGroupSettingsList = null;
	private List<DisabledId> mDisabledIdList = null;
	private List<String> mActiveForwardingList = null;
	private List<WebCallButton> mWebCallButtonList = null;
	private String mDefaultGreetingId;
	private List<String> mGroupList = null;
	private List<EmailAddress> mEmailAddressList = null;
	private String mBaseUrl = null;

	public Settings(String json) {
		super();
		mPhoneList = getPhoneListFromJson(json);
		mVoicemailGreetingsList = getGreetingsListFromJson(json);
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
			for (Iterator<Greeting> iterator = mVoicemailGreetingsList.iterator(); iterator.hasNext();) {
				Greeting element = (Greeting) iterator.next();
				ret+=element.toJson();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="]";
		}
		
		if(mGroupSettingsList.size()>0) {
			ret+=",\"groups\":{";
			for (Iterator<Group> iterator = mGroupSettingsList.iterator(); iterator.hasNext();) {
				Group element = (Group) iterator.next();
				ret+=element.toJson();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="}";
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		String ret="";

		if(mPhoneList.size()>0) {
			ret+="\"phones\":{";
			for (Iterator<Phone> iterator = mPhoneList.iterator(); iterator.hasNext();) {
				Phone element = (Phone) iterator.next();
				ret+=element.toString(); //TODO change to toJson when implemented
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="\n"; 
		}
		
		if(mVoicemailGreetingsList.size()>0) {
			ret+=",\"greetings\":[";
			for (Iterator<Greeting> iterator = mVoicemailGreetingsList.iterator(); iterator.hasNext();) {
				Greeting element = (Greeting) iterator.next();
				ret+=element.toString();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="]\n";
		}
		
		if(mGroupSettingsList.size()>0) {
			ret+=",\"groups\":{";
			for (Iterator<Group> iterator = mGroupSettingsList.iterator(); iterator.hasNext();) {
				Group element = (Group) iterator.next();
				ret+=element.toString();
				if(iterator.hasNext()) {
					ret+=",";
				}
			}
			ret+="}\n";
		}
		
		return ret;
	}

	/**
	 * Return the List of Group from json
	 * @param json
	 * @return
	 */
	private List<Group> getGroupSettingsListFromJson(String json) {
		List<Group> result = new ArrayList<Group>();
		try {
			result = Group.createGroupSettingsFromJsonResponse(json);
		} catch (Exception e) {
			System.out.println("Error in groupSetting object creation.");
		}
		return result;
	}
	
	/**
	 * Return the List of Voicemail Greetings from json
	 * 
	 */
	private List<Greeting> getGreetingsListFromJson(String json) {
		List<Greeting> result = new ArrayList<Greeting>();
		try {
			result = Greeting.createGroupSettingsFromJsonResponse(json);
		} catch (Exception e) {
			System.out.println("Error in GreetingSetting object creation.");
		}
		return result;
	}
	
	/**
	 * Return the List of Phones from json
	 * TODO implement correctly
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
	public List<Greeting> getVoicemailGreetingsList() {
		return mVoicemailGreetingsList;
	}

	/**
	 * @return the mGroupSettingsList
	 */
	public List<Group> getGroupSettingsList() {
		return mGroupSettingsList;
	}

	/**
	 * @return the mDisabledIdList
	 */
	public List<DisabledId> getDisabledIdList() {
		return mDisabledIdList;
	}

	/**
	 * @return the mWebCallButtonList
	 */
	public List<WebCallButton> getWebCallButtonList() {
		return mWebCallButtonList;
	}

	/**
	 * @return the mDefaultGreetingId
	 */
	public String getDefaultGreetingId() {
		return mDefaultGreetingId;
	}

	/**
	 * @return the mGroupList
	 */
	public List<String> getGroupList() {
		return mGroupList;
	}

	/**
	 * @return the mEmailAddressList
	 */
	public List<EmailAddress> getEmailAddressList() {
		return mEmailAddressList;
	}

	/**
	 * @return the mBaseUrl
	 */
	public String getBaseUrl() {
		return mBaseUrl;
	}

	/**
	 * @return the mActiveForwardingList
	 */
	public List<String> getActiveForwardingList() {
		return mActiveForwardingList;
	}
	
	
	
	
}
