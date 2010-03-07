package com.techventus.server.voice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.techventus.server.voice.datatypes.Phone;

public class Voice {

	public boolean PRINT_TO_CONSOLE = true;
	public List<Phone> phoneList = null;
	String general = null;
	String rnrSEE = null;
	String source = null;
	String user = null;
	String pass = null;
	String authToken = null;
	static String generalURLString = "https://www.google.com/voice/";
	static String inboxURLString = "https://www.google.com/voice/inbox/recent/inbox/";
	static String starredURLString = "https://www.google.com/voice/inbox/recent/starred/";
	static String recentAllURLString = "https://www.google.com/voice/inbox/recent/all/";
	static String spamURLString = "https://www.google.com/voice/inbox/recent/spam/";
	static String trashURLString = "https://www.google.com/voice/inbox/recent/spam/";
	static String voicemailURLString = "https://www.google.com/voice/inbox/recent/voicemail/";
	static String smsURLString = "https://www.google.com/voice/inbox/recent/sms/";
	static String recordedURLString = "https://www.google.com/voice/inbox/recent/recorded/";
	static String placedURLString = "https://www.google.com/voice/inbox/recent/placed/";
	static String receivedURLString = "https://www.google.com/voice/inbox/recent/received/";
	static String missedURLString = "https://www.google.com/voice/inbox/recent/missed/";
	static String phoneEnableURLString = "https://www.google.com/voice/settings/editDefaultForwarding/";

	@Deprecated
	public Voice(String user, String pass, String source, String rnrSee)
			throws IOException {

		this.user = user;
		this.pass = pass;
		this.rnrSEE = rnrSee;
		this.source = source;

		login();
	}
	public Voice(String user, String pass, String source)
			throws IOException {
		
		this.user = user;
		this.pass = pass;
		//this.rnrSEE = rnrSee;
		this.source = source;
		
		login();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		general = getGeneral();
		setRNRSEE();
		setPhoneInfo();
	}
	
	public Voice(String user, String pass)
			throws IOException {
	    this.PRINT_TO_CONSOLE = false;
		this.user = user;
		this.pass = pass;
		//this.rnrSEE = rnrSee;
		this.source = "GoogleVoiceJava";
		
		login();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		general = getGeneral();
		setRNRSEE();
		setPhoneInfo();
	}
	
	public Voice(String user, String pass, boolean printDebugIntoToSystemOut)
			throws IOException {
		this.PRINT_TO_CONSOLE = printDebugIntoToSystemOut;
		this.user = user;
		this.pass = pass;
		//this.rnrSEE = rnrSee;
		this.source = "GoogleVoiceJava";
		
		login();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		general = getGeneral();
		setRNRSEE();
		setPhoneInfo();
		}

	//public Voice(){
	//	authToken = "abcde";
	//}
	
	public String getInbox() throws IOException{
		return get(inboxURLString);
	}
	public String getGeneral() throws IOException{
		return get(generalURLString);
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
	

	private void setRNRSEE(){
		if(general!=null){
			String p1 = general.split("'_rnr_se': '",2)[1];
			rnrSEE = p1.split("',",2)[0];
			p1=null;
		}
	}
	
	private void setPhoneInfo(){
		if(general!=null){
			List<Phone> phoneList = new ArrayList<Phone>();
			String p1 = general.split("'phones':",2)[1];
			p1 = (p1.split("'_rnr_se'", 2))[0];
			String[] a = p1.split("\\{\"id\"\\:");
			//if(PRINT_TO_CONSOLE) System.out.println(a[0]);
			for(int i=1;i<a.length;i++){
				Phone phone = new Phone();
				String[] b = a[i].split(",\"wd\"\\:\\{", 2)[0].split(",");
				phone.id = Integer.parseInt(b[0].replaceAll("\"", ""));
				for(int j=0;j<b.length;j++){
					if(b[j].contains("phoneNumber")){
						phone.number = b[j].split("\\:")[1].replaceAll("\"", "");
					}else if(b[j].contains("type")){
						phone.type = b[j].split("\\:")[1].replaceAll("\"", "");
					}else if(b[j].contains("name")){
						phone.name = b[j].split("\\:")[1].replaceAll("\"", "");
					
					}else if(b[j].contains("formattedNumber")){
						phone.formattedNumber = b[j].split("\\:")[1].replaceAll("\"", "");
					}else if(b[j].contains("carrier")){
						phone.carrier = b[j].split("\\:")[1].replaceAll("\"", "");
					}else if(b[j].contains("\"verified")){
						phone.verified = Boolean.parseBoolean(b[j].split("\\:")[1].replaceAll("\"", ""));
					}
				}
				phoneList.add(phone);
			}
			
			this.phoneList = phoneList;
			
		}
	}
	
	
	
	
	
	public String call(String originNumber, String destinationNumber, String phoneType)
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
		calldata += "&" + URLEncoder.encode("phoneType", "UTF-8") + "="
		+ URLEncoder.encode(phoneType, "UTF-8");
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
		callconn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

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

	public String cancelCall(String originNumber, String destinationNumber, String phoneType)
			throws IOException {
		String out = "";
		String calldata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		calldata += "&" + URLEncoder.encode("outgoingNumber", "UTF-8") + "="
				+ URLEncoder.encode("undefined", "UTF-8");
		calldata += "&" + URLEncoder.encode("forwardingNumber", "UTF-8") + "="
				+ URLEncoder.encode("undefined", "UTF-8");


		calldata += "&" + URLEncoder.encode("cancelType", "UTF-8") + "="
		+ URLEncoder.encode("C2C", "UTF-8");
		calldata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		// POST /voice/call/connect/ outgoingNumber=[number to
		// call]&forwardingNumber=[forwarding
		// number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from
		// page]
		URL callURL = new URL("https://www.google.com/voice/call/cancel/");

		URLConnection callconn = callURL.openConnection();
		callconn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

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

	public String phoneDisable(int ID)
			throws IOException {
		String out = "";
		
		String disabledata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		disabledata += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
				+ URLEncoder.encode("0", "UTF-8");
		disabledata += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(ID), "UTF-8");
		disabledata+= "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
		+ URLEncoder.encode(rnrSEE, "UTF-8");
		// POST /voice/call/connect/ outgoingNumber=[number to
		// call]&forwardingNumber=[forwarding
		// number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from
		// page]
		
		//
		URL disableURL = new URL(phoneEnableURLString);

		if(PRINT_TO_CONSOLE) System.out.println(disabledata);
		
		URLConnection disableconn = disableURL.openConnection();
		disableconn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

		disableconn.setDoOutput(true);
		disableconn.setDoInput(true);
		
		
		
		OutputStreamWriter callwr = new OutputStreamWriter(disableconn
				.getOutputStream());
		callwr.write(disabledata);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(
				disableconn.getInputStream()));

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
	
	public String phoneEnable(int ID)
			throws IOException {
		String out = "";
		
		String disabledata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		disabledata += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8");
		disabledata += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(ID), "UTF-8");
		disabledata+= "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
		+ URLEncoder.encode(rnrSEE, "UTF-8");
		// POST /voice/call/connect/ outgoingNumber=[number to
		// call]&forwardingNumber=[forwarding
		// number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from
		// page]
		
		//
		URL enableURL = new URL(phoneEnableURLString);

		if(PRINT_TO_CONSOLE) System.out.println(disabledata);
		
		URLConnection enableconn = enableURL.openConnection();
		enableconn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

		enableconn.setDoOutput(true);
		enableconn.setDoInput(true);
		
		
		
		OutputStreamWriter callwr = new OutputStreamWriter(enableconn
				.getOutputStream());
		callwr.write(disabledata);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(
				enableconn.getInputStream()));

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
				+ URLEncoder.encode(txt,"UTF-8");
		smsdata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		URL smsurl = new URL("https://www.google.com/voice/sms/send/");

		URLConnection smsconn = smsurl.openConnection();
		smsconn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

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
		conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

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
			// if(PRINT_TO_CONSOLE) System.out.println(line);
			if (line.contains("Auth=")) {
				this.authToken = line.split("=", 2)[1].trim();
				if(PRINT_TO_CONSOLE) System.out.println("AUTH TOKEN =" + this.authToken);
			}
		}
		wr.close();
		rd.close();

		if (this.authToken == null) {
			throw new IOException("No Authorisation Received.");
		}
	}

	
	
	public boolean isLoggedIn(){
		String res;
		try {
			res = getRecent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if(res.contains("<meta name=\"description\" content=\"Google Voice gives you one number") && 
				res.contains("action=\"https://www.google.com/accounts/ServiceLoginAuth?service=grandcentral\"")){
			return false;
		}else{
			if(res.contains("Enter a new or existing contact name") || res.contains("<json><![CDATA[")){
				return true;
			}
		}
		return false;
	}
	
	
}
