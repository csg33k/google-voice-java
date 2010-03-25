package com.techventus.server.voice.datatypes;

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
