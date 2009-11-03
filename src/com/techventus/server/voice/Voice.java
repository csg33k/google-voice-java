package com.techventus.server.voice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Voice {

	String rnrSEE;
	String user;
	String pass;
	String authToken;
	
	
	
	public Voice(String user, String pass, String source) throws IOException{
		
		this.user = user;
		this.pass = pass;
		
		
		
        String data = URLEncoder.encode("accountType", "UTF-8") + "=" + URLEncoder.encode("GOOGLE", "UTF-8");
        data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
        data += "&" + URLEncoder.encode("Passwd", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
        data += "&" + URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode("grandcentral", "UTF-8");
        data += "&" + URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode(source, "UTF-8");
	    
        // Send data
        URL url = new URL("https://www.google.com/accounts/ClientLogin");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
    
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        
        String AuthToken = "";
        while ((line = rd.readLine()) != null) {
           // System.out.println(line);
            if(line.contains("Auth=")){
            	AuthToken = line.split("=",2)[1].trim();
            	System.out.println("AUTH TOKEN ="+AuthToken);
            }
        }
        wr.close();
        rd.close();
		
		
		
		
		
		
	}
	
	public String call(String originNumber, String destinationNumber, String logSource) throws IOException{
			String out = "";
		   	String calldata = URLEncoder.encode("auth","UTF-8")+"="+URLEncoder.encode(authToken, "UTF-8");
		   	calldata += "&" +  URLEncoder.encode("outgoingNumber", "UTF-8") + "=" + URLEncoder.encode(destinationNumber, "UTF-8");
		   	calldata += "&" + URLEncoder.encode("forwardingNumber", "UTF-8") + "=" + URLEncoder.encode(originNumber, "UTF-8");
		   	calldata += "&" + URLEncoder.encode("subscriberNumber", "UTF-8") + "=" + URLEncoder.encode("undefined", "UTF-8");
		   	calldata += "&" + URLEncoder.encode("remember", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
		   	calldata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "=" + URLEncoder.encode(rnrSEE, "UTF-8");
		        	//POST /voice/call/connect/ outgoingNumber=[number to call]&forwardingNumber=[forwarding number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from page]
		    URL callURL = new URL("https://www.google.com/voice/call/connect/");
		    
		    URLConnection callconn = callURL.openConnection();
		    callconn.setDoOutput(true);
        	OutputStreamWriter callwr = new   OutputStreamWriter(callconn.getOutputStream());
        	callwr.write(calldata);
        	callwr.flush();
        	
        	BufferedReader callrd = new BufferedReader(new InputStreamReader(callconn.getInputStream()));
        	
        	String line;
	        while ((line = callrd.readLine()) != null) {
	           out+=line+"\n\r";

	        }
        
	        callwr.close();
	        callrd.close();
	
	        if(out.equals("")){
	        	throw new IOException("No Response Data Received.");
	        }
	        
	        return out;
	    
	}
	
	
	
	public void login(){
		//set authToken
	}
	
	
}
