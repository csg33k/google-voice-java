package com.techventus.server.voice;

public class Phone {
	
	protected String id;
	protected String number;
	protected String formattedNumber;
	protected String type;
	protected String name;
	protected String carrier;
	protected Boolean verified;
	
	public Phone(){
		
	}
	
	
	public String toString(){
		String ret = "";
		if(id!=null){
			ret+="id="+id+";";
		}
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
