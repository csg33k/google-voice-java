package com.techventus.server.voice.datatypes;

public class Phone {
	
	public int id;
	public String number;
	public String formattedNumber;
	public String type;
	public String name;
	public String carrier;
	public Boolean verified;
	
	public Phone(){
		
	}
	
	
	public String toString(){
		String ret = "";
		ret+="id="+id+";";
		if(number!=null){
			ret+="number="+number+";";
		}
		if(name!=null){
			ret+="name="+name+";";
		}
		if(carrier!=null){
			ret+="carrier="+carrier+";";
		}
		if(type!=null){
			ret+="type="+type+";";
		}
		if(verified!=null){
			ret+="verified="+verified+";";
		}
		if(formattedNumber!=null){
			ret+="formattedNumber="+formattedNumber+";";
		}
		
		return ret;
		
	}
	
	
}
