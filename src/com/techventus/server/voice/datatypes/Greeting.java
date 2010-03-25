package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.techventus.server.voice.util.ParsingUtil;

/**
 * 
 * TODO Add jobberName 
 * @author Tobias Eisentraeger
 *
 */
public class Greeting {
	
	
	private String id;
	private String name;
	private String jobberName;

	public Greeting(String id,String name){
		this.id = id;
		this.name = name;
		this.jobberName = "";
	}
	
	public String toString(){
		String ret="{id="+id+";";
		ret+="name="+name+",";	
		ret+="jobberName="+jobberName+"}";	
		return ret;
	}
	
	/**
	 * Return a json representation of the object
	 * {"id":"0","name":"System Standard","jobberName":""}
	 * @return
	 */
	public String toJson() {
		String ret="";
		if(id.equals("0")) ret+="{\"id\":\""+id+"\","; //Error in gvoice json format, 0 has " "
		else ret+="{\"id\":"+id+",";
		ret+="\"name\":\""+name+"\",";
		ret+="\"jobberName\":\""+jobberName+"\"}";
		return ret;
	}
	
	public final static List<Greeting> createGroupSettingsFromJsonResponse(String json) {
		List<Greeting> result = new ArrayList<Greeting>();
		json = ParsingUtil.removeUninterestingParts(json, "\"greetings\":[", "],", false);
		String[] greetingsStrings = json.split(Pattern.quote("},{"));
		// Add System standard greeting
		result.add(new Greeting("0", "System Standard"));
		for (int i = 1; i < greetingsStrings.length; i++) {
			String lId =   ParsingUtil.removeUninterestingParts(greetingsStrings[i]  , "\"id\":"  , ",", false);
			String lName = ParsingUtil.removeUninterestingParts(greetingsStrings[i], "\"name\":\"", "\",\"", false);
			Greeting lGreeting = new Greeting(lId, lName);
			result.add(lGreeting);
		}
		return result;
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
	
	
	
	
}
