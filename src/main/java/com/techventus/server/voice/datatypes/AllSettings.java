package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.techventus.server.voice.util.ParsingUtil;

public class AllSettings{

	private int[] phoneList;
   	private Phone[] phones;
   	private Setting settings;


	@VisibleForTesting
	public AllSettings(JsonObject jsonObject) {
	   	phoneList = ParsingUtil.jsonIntArrayToIntArray(jsonObject.get("phoneList").getAsJsonArray());

   		phones  = Phone.createArrayFromJsonObject(jsonObject.get("phones").getAsJsonObject());

   		settings  = new Setting(jsonObject.get("settings").getAsJsonObject());
	}

   	public AllSettings(String json) {
	    if(ParsingUtil.isJsonEmpty(json))
		    return;

   		JsonObject lObj = new Gson().fromJson(json, JsonObject.class);

   		phoneList = ParsingUtil.jsonIntArrayToIntArray(lObj.get("phoneList").getAsJsonArray());

   		phones  = Phone.createArrayFromJsonObject(lObj.get("phones").getAsJsonObject());

   		settings  = new Setting(lObj.get("settings").getAsJsonObject());
   	}

   	public JsonObject toJsonObject() {
	    Map map = Maps.newHashMap();

	    if(phoneList != null)
		    map.put("phoneList", phoneList);
	    if(phones != null) {
		    map.put("phones",Phone.phonesArrayToJsonObject(phones));
	    }
	    if(settings != null ) {
		    map.put("settings", settings.toJsonObject());
	    }
	    return new Gson().toJsonTree(map).getAsJsonObject();
    }

   	//TODO Perhaps this should throw an Exception rather than return false
   	/**
   	 *
   	 * Query disabled status - if id not found, then it returned false, which normally means enabled.
   	 * @param phoneId
   	 * @return true if the Phone is Disabled. Otherwise it return false.
   	 */
   	public boolean isPhoneDisabled(int phoneId) {
   		boolean ret = false;
   		try {
 			if(settings.getmDisabledIdList()!=null) {
	   			for (int i = 0; i < settings.getmDisabledIdList().length; i++) {
	 				if(settings.getmDisabledIdList()[i].getId().equals(phoneId+"")) {
	 					ret = true;
	 				}
				}
 			} else {
 				// list is empty, we will return false
 				ret = false;
 			}
 		} catch (NullPointerException e) {
 			ret = false;
 		}
 		return ret;
   	}

   	public void setPhoneDisabled(int phoneId) {
   		for (DisabledId lDisId : settings.getmDisabledIdList()) {
			if(lDisId.getId().equals(phoneId+"")) {
				lDisId.setDisabled(true);
				return;
			}
		}
   		// if not in array we create a new one
   		DisabledId[] lNewDisabledList = new DisabledId[settings.getmDisabledIdList().length+1];
   		for (int i = 0; i < settings.getmDisabledIdList().length; i++) {
   			lNewDisabledList[i] = settings.getmDisabledIdList()[i];
		}
   		lNewDisabledList[lNewDisabledList.length-1] = new DisabledId(phoneId+"", true);
   		settings.setmDisabledIdList(lNewDisabledList);
   	}

	public void setPhoneEnabled(int phoneId) {
   		for (DisabledId lDisId : settings.getmDisabledIdList()) {
			if(lDisId.getId().equals(phoneId+"")) {
				lDisId.setDisabled(false);
				return;
			}
		}
   		// if not in array we create a new one
   		DisabledId[] lNewDisabledList = new DisabledId[settings.getmDisabledIdList().length+1];
   		for (int i = 0; i < settings.getmDisabledIdList().length; i++) {
   			lNewDisabledList[i] = settings.getmDisabledIdList()[i];
		}
   		lNewDisabledList[lNewDisabledList.length-1] = new DisabledId(phoneId+"", false);
   		settings.setmDisabledIdList(lNewDisabledList);
   	}

	/**
  	 *
  	 * Query smsEnabled status - if id not found, then it returnes false
  	 * @param phoneId
  	 * @return true if the Phone is smsEnabled. Otherwise it returns false.
  	 */
  	public boolean isPhoneSmsEnabled(int phoneId) {
  		boolean ret = false;
  		try {
			for (int i = 0; i < phones.length; i++) {
				if(phones[i].getId() == phoneId) {
					ret = phones[i].getSmsEnabled();
				}
			}
		} catch (NullPointerException e) {
			ret = false;
		}
		return ret;
  	}

	/**
	 * @return the phoneList
	 */
	public int[] getPhoneList() {
		return phoneList;
	}

	/**
	 * @return the phoneList sorted
	 */
	public int[] getPhoneListSorted() {
		Arrays.sort(phoneList);
		return phoneList;
	}

	/**
	 * @return the phoneList as List<Integer>
	 */
	public List<Integer> getPhoneListAsList() {
		List<Integer> lresult = new ArrayList<Integer>();
		for (int i = 0; i < phoneList.length; i++) {
			lresult.add(phoneList[i]);
		}
		return lresult;
	}

	/**
	 * @return the phones
	 */
	public Phone[] getPhones() {
		return phones;
	}

	/**
	 * @return the phones sorted by their id number
	 */
	public Phone[] getPhonesSorted() {
		Arrays.sort(phones);
		return phones;
	}

	/**
	 * @return the settings
	 */
	public Setting getSettings() {
		return settings;
	}

	/**
	 * @param phoneList the phoneList to set
	 */
	public void setPhoneList(int[] phoneList) {
		this.phoneList = phoneList;
	}

	/**
	 * @param phones the phones to set
	 */
	public void setPhones(Phone[] phones) {
		this.phones = phones;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Setting settings) {
		this.settings = settings;
	}




   	/*
{
    "phoneList": [
        1,
        2
    ],
    "phones": {
        "1": {
            "active": true,
            "behaviorOnRedirect": 0,
            "carrier": "",
            "customOverrideState": 0,
            "dEPRECATEDDisabled": false,
            "enabledForOthers": true,
            "formattedNumber": "(912) 342-2319",
            "id": 1,
            "incomingAccessNumber": "",
            "name": "SkypeUS#",
            "phoneNumber": "+19123422319",
            "policyBitmask": 0,
            "redirectToVoicemail": false,
            "scheduleSet": false,
            "smsEnabled": false,
            "telephonyVerified": true,
            "type": 1,
            "verified": true,
            "voicemailForwardingVerified": false,
            "wd": {
                "allDay": false,
                "times": []
            },
            "we": {
                "allDay": false,
                "times": []
            },
            "weekdayAllDay": false,
            "weekdayTimes": [],
            "weekendAllDay": false,
            "weekendTimes": []
        },
        "2": {
            "active": true,
            "behaviorOnRedirect": 0,
            "carrier": "",
            "customOverrideState": 0,
            "dEPRECATEDDisabled": false,
            "enabledForOthers": true,
            "formattedNumber": "(747) 477-2719",
            "id": 2,
            "incomingAccessNumber": "",
            "name": "Gizmo5",
            "phoneNumber": "+17474772719",
            "policyBitmask": 0,
            "redirectToVoicemail": false,
            "scheduleSet": false,
            "smsEnabled": false,
            "telephonyVerified": true,
            "type": 7,
            "verified": true,
            "voicemailForwardingVerified": false,
            "wd": {
                "allDay": false,
                "times": []
            },
            "we": {
                "allDay": false,
                "times": []
            },
            "weekdayAllDay": false,
            "weekdayTimes": [],
            "weekendAllDay": false,
            "weekendTimes": []
        }
    },
    "settings": {"settings": {
        "activeForwardingIds": [
            1,
            2
        ],
        "baseUrl": "https://www.google.com/voice",
        "credits": 1000,
        "defaultGreetingId": 0,
        "didInfos": [],
        "directConnect": false,
        "disabledIdMap": [{
            "disabled": true,
            "id": "6"
        }],
        "doNotDisturb": false,
        "emailAddresses": [{}],
        "emailNotificationActive": true,
        "emailNotificationAddress": "teisentraeger@gmail.com",
        "greetings": [
            {
                "id": "0",
                "name": "System Standard"
            },
            {
                "id": "2",
                "name": "Testgreeting 1"
            },
            {
                "id": "3",
                "name": "Testgreeting 2"
            }
        ],
        "groupList": [
            "15",
            "13",
            "14",
            "2934992504966866256",
            "7546022145019939053",
            "4645700645618079676",
            "8611236118126468131"
        ],
        "groups": {
            "13": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 2,
                "id": "13",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": true,
                "name": "Friends"
            },
            "14": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 0,
                "id": "14",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": false,
                "name": "Family"
            },
            "15": {
                "directConnect": true,
                "disabledForwardingIds": {"1": true},
                "greetingId": 0,
                "id": "15",
                "isCustomDirectConnect": true,
                "isCustomForwarding": true,
                "isCustomGreeting": false,
                "name": "Coworkers"
            },
            "2934992504966866256": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 0,
                "id": "2934992504966866256",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": false,
                "name": "GMail Contacts only - no phone sync"
            },
            "4645700645618079676": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 0,
                "id": "4645700645618079676",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": false,
                "name": "Mailing Addresses"
            },
            "7546022145019939053": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 0,
                "id": "7546022145019939053",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": false,
                "name": "Starred in Android"
            },
            "8611236118126468131": {
                "directConnect": false,
                "disabledForwardingIds": {},
                "greetingId": 0,
                "id": "8611236118126468131",
                "isCustomDirectConnect": false,
                "isCustomForwarding": false,
                "isCustomGreeting": false,
                "name": "Church"
            }
        },

        "language": "en",
        "primaryDid": "+19122891115",
        "screenBehavior": 0,
        "showTranscripts": true,
        "smsNotifications": "[]",
        "smsToEmailActive": true,
        "smsToEmailSubject": false,
        "spam": 0,
        "timezone": "Europe/Berlin",
        "useDidAsCallerId": false,
        "useDidAsSource": true
    }}
}

	*/


}
