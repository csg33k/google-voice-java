package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.techventus.server.voice.util.ParsingUtil;

/**
 *
 * TODO Add jobberName
 * @author Tobias Eisentraeger
 *
 */
public class Greeting implements Comparable<Greeting> {


	private int id;
	private String name;
	private String jobberName;

	public Greeting(int id,String name){
		this.id = id;
		this.name = name;
		this.jobberName = "";
	}

	public Greeting(int id,String jobberName, String name){
		this.id = id;
		this.name = name;
		this.jobberName = jobberName;
	}

	public String toString(){
		String ret="{id="+id+";";
		ret+="name="+name+",";
		ret+="jobberName="+jobberName+"}";
		return ret;
	}


	public final static List<Greeting> createGroupSettingsFromJsonResponse(String json) {
		List<Greeting> result = new ArrayList<Greeting>();
		json = ParsingUtil.removeUninterestingParts(json, "\"greetings\":[", "],", false);
		String[] greetingsStrings = json.split(Pattern.quote("},{"));
		// Add System standard greeting
		result.add(new Greeting(0, "System Standard"));
		for (int i = 1; i < greetingsStrings.length; i++) {
			int lId =   Integer.parseInt(ParsingUtil.removeUninterestingParts(greetingsStrings[i]  , "\"id\":"  , ",", false));
			String lName = ParsingUtil.removeUninterestingParts(greetingsStrings[i], "\"name\":\"", "\",\"", false);
			Greeting lGreeting = new Greeting(lId, lName);
			result.add(lGreeting);
		}
		return result;
	}

	/*
"greetings": [
            {
                "id": "0",
                "jobberName": "",
                "name": "System Standard"
            },
            {
                "id": 2,
                "jobberName": "",
                "name": "Testgreeting 1"
            },
            {
                "id": 3,
                "jobberName": "47ee52c084.3.greeting.mulaw",
                "name": "Testgreeting 2"
            }
        ],
	 */
	public static List<Greeting> createListFromJsonObject(JsonObject settingsJSON) throws IllegalStateException {
		List<Greeting> greetingss = Lists.newArrayList();
		if(settingsJSON.has("greetings")) {
			JsonElement greetingObject = settingsJSON.get("greetings");

			JsonArray lArray = greetingObject.isJsonArray() ?  settingsJSON.get("greetings").getAsJsonArray() : new JsonArray();
			for (int i = 0; i < lArray.size(); i++) {
				int lId = lArray.get(i).getAsJsonObject().get("id").getAsInt();
				String lJobberName= lArray.get(i).getAsJsonObject().get("jobberName").getAsString();
				String lName = lArray.get(i).getAsJsonObject().get("name").getAsString();
				greetingss.add(new Greeting(lId,lJobberName,lName));
			}
		}

		return greetingss;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the jobberName
	 */
	public String getJobberName() {
		return jobberName;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param jobberName the jobberName to set
	 */
	public void setJobberName(String jobberName) {
		this.jobberName = jobberName;
	}

	//TODO dotn create list first, direct transform
	public final static Greeting[] createArrayFromJsonObject(JsonObject settingsJSON) {
		List<Greeting> tList = createListFromJsonObject(settingsJSON);
		return (Greeting[]) tList.toArray(new Greeting[tList.size()]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Greeting o) {
		if( id < (o.getId() ))
            return -1;
        if( id > (o.getId() ))
            return 1;

        return 0;
	}




}
