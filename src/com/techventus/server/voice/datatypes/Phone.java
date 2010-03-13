package com.techventus.server.voice.datatypes;

public class Phone {
	
	
	public int id;
	public String number;
	public String formattedNumber;
	public String type;
	public String name;
	public String carrier;
	public Boolean verified;
	
	/**
	 * Instantiates a new empty Phone object.  This method is deprecated.
	 * Consider using Phone(int id,String number,String formattedNumber,String type,String name,String carrier, Boolean verified)
	 */
	@Deprecated
	public Phone(){
		
	}
	
	public Phone(int id,String number,String formattedNumber,String type,String name,String carrier, Boolean verified){
		this.id = id;
		this.number = number;
		this.formattedNumber = formattedNumber;
		this.type = type;
		this.name = name;
		this.carrier = carrier;
		this.verified = verified;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getNumber(){
		return this.number;
	}
	
	public void setNumber(String number){
		this.number = number;
	}
	public String getFormattedNumber(){
		return this.formattedNumber;
	}
	public void setFormattedNumber(String formattedNumber){
		this.formattedNumber = formattedNumber;
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
