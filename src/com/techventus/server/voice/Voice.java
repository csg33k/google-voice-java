package com.techventus.server.voice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Voice {

	String rnrSEE = null;
	String source = null;
	String user = null;
	String pass = null;
	String authToken = null;
	String inboxURLString = "https://www.google.com/voice/inbox/recent/inbox/";
	String starredURLString = "https://www.google.com/voice/inbox/recent/starred/";
	String recentAllURLString = "https://www.google.com/voice/inbox/recent/all/";
	String spamURLString = "https://www.google.com/voice/inbox/recent/spam/";
	String trashURLString = "https://www.google.com/voice/inbox/recent/spam/";
	String voicemailURLString = "https://www.google.com/voice/inbox/recent/voicemail/";
	String smsURLString = "https://www.google.com/voice/inbox/recent/sms/";
	String recordedURLString = "https://www.google.com/voice/inbox/recent/recorded/";
	String placedURLString = "https://www.google.com/voice/inbox/recent/placed/";
	String receivedURLString = "https://www.google.com/voice/inbox/recent/received/";
	String missedURLString = "https://www.google.com/voice/inbox/recent/missed/";

	public String getInbox() throws IOException{
		return get(inboxURLString);
	}
	public String getStarred() throws IOException{
		return get(starredURLString);
	}
	public String getRecent() throws IOException{
		return get(recentAllURLString);
	}
	public String getSpam() throws IOException{
		return get(spamURLString);
	}
	public String getRecorded() throws IOException{
		return get(recordedURLString);
	}
	public String getPlaced() throws IOException{
		return get(placedURLString);
	}
	public String getReceived() throws IOException{
		return get(receivedURLString);
	}
	public String getMissed() throws IOException{
		return get(missedURLString);
	}
	public String getSMS() throws IOException{
		return get(smsURLString);
	}
	
	public Voice(String user, String pass, String source, String rnrSee)
			throws IOException {

		this.user = user;
		this.pass = pass;
		this.rnrSEE = rnrSee;
		this.source = source;

		login();
	}
	
	
	

	public String call(String originNumber, String destinationNumber)
			throws IOException {
		String out = "";
		String calldata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		calldata += "&" + URLEncoder.encode("outgoingNumber", "UTF-8") + "="
				+ URLEncoder.encode(destinationNumber, "UTF-8");
		calldata += "&" + URLEncoder.encode("forwardingNumber", "UTF-8") + "="
				+ URLEncoder.encode(originNumber, "UTF-8");
		calldata += "&" + URLEncoder.encode("subscriberNumber", "UTF-8") + "="
				+ URLEncoder.encode("undefined", "UTF-8");
		calldata += "&" + URLEncoder.encode("remember", "UTF-8") + "="
				+ URLEncoder.encode("0", "UTF-8");
		calldata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		// POST /voice/call/connect/ outgoingNumber=[number to
		// call]&forwardingNumber=[forwarding
		// number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from
		// page]
		URL callURL = new URL("https://www.google.com/voice/call/connect/");

		URLConnection callconn = callURL.openConnection();
		callconn.setDoOutput(true);
		OutputStreamWriter callwr = new OutputStreamWriter(callconn
				.getOutputStream());
		callwr.write(calldata);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(
				callconn.getInputStream()));

		String line;
		while ((line = callrd.readLine()) != null) {
			out += line + "\n\r";

		}

		callwr.close();
		callrd.close();

		if (out.equals("")) {
			throw new IOException("No Response Data Received.");
		}

		return out;

	}

	public String sendSMS(String destinationNumber, String txt) throws IOException {
		String out = "";
		String smsdata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");

		smsdata += "&" + URLEncoder.encode("phoneNumber", "UTF-8") + "="
				+ URLEncoder.encode(destinationNumber, "UTF-8");
		smsdata += "&"
				+ URLEncoder.encode("text", "UTF-8")
				+ "="
				+ URLEncoder
						.encode(
								txt,
								"UTF-8");
		smsdata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		URL smsurl = new URL("https://www.google.com/voice/sms/send/");

		URLConnection smsconn = smsurl.openConnection();
		smsconn.setDoOutput(true);
		OutputStreamWriter callwr = new OutputStreamWriter(smsconn
				.getOutputStream());
		callwr.write(smsdata);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(
				smsconn.getInputStream()));

		String line;
		while ((line = callrd.readLine()) != null) {
			out += line + "\n\r";

		}

		callwr.close();
		callrd.close();

		if (out.equals("")) {
			throw new IOException("No Response Data Received.");
		}

		return out;
	}

	

	

	
	String get(String urlString) throws IOException{
		URL url = new URL(urlString+"?auth="+URLEncoder.encode(authToken,"UTF-8"));
		URLConnection conn = url.openConnection ();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			sb.append(line+"\n\r");
		}
		rd.close();
		String result = sb.toString();
		
		
		return result;
	}
	
	public void login() throws IOException{

		String data = URLEncoder.encode("accountType", "UTF-8") + "="
				+ URLEncoder.encode("GOOGLE", "UTF-8");
		data += "&" + URLEncoder.encode("Email", "UTF-8") + "="
				+ URLEncoder.encode(user, "UTF-8");
		data += "&" + URLEncoder.encode("Passwd", "UTF-8") + "="
				+ URLEncoder.encode(pass, "UTF-8");
		data += "&" + URLEncoder.encode("service", "UTF-8") + "="
				+ URLEncoder.encode("grandcentral", "UTF-8");
		data += "&" + URLEncoder.encode("source", "UTF-8") + "="
				+ URLEncoder.encode(source, "UTF-8");

		// Send data
		URL url = new URL("https://www.google.com/accounts/ClientLogin");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		String line;

		//String AuthToken = null;
		while ((line = rd.readLine()) != null) {
			// System.out.println(line);
			if (line.contains("Auth=")) {
				this.authToken = line.split("=", 2)[1].trim();
				System.out.println("AUTH TOKEN =" + this.authToken);
			}
		}
		wr.close();
		rd.close();

		if (this.authToken == null) {
			throw new IOException("No Authorisation Received.");
		}
	}

}
