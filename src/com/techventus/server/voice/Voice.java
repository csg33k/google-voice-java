package com.techventus.server.voice;

public class Voice {

	String rnrSEE;
	String user;
	String pass;
	String authToken;
	
	
	
	public Voice(String user, String pass, String rnrSEE){
		this.rnrSEE = rnrSEE;
		this.user = user;
		this.pass = pass;
	}
	
	public String call(String originNumber, String destinationNumber, String logSource){
		return null;
	}
	
	
	
	public void login(){
		//set authToken
	}
	
	
}
