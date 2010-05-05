/*
 * Voice.java
 *
 * Created: Sat Mar  13 14:41:11 2010
 *
 * Copyright (C) 2010 Techventus, LLC
 * 
 * Techventus, LLC is not responsible for any use or misuse of this product.
 * In using this software you agree to hold harmless Techventus, LLC and any other
 * contributors to this project from any damages or liabilities which might result 
 * from its use.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.techventus.server.voice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import gvjava.org.json.JSONException;

import com.techventus.server.voice.datatypes.AllSettings;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.PhoneOld;
import com.techventus.server.voice.datatypes.Greeting;
import com.techventus.server.voice.util.ParsingUtil;

/**
 * The Class Voice. This class is the basis of the entire API and contains all
 * the components necessary to connect and authenticate with Google Voice, place
 * calls and SMS, and pull in the raw data from the account.
 * 
 * @author Techventus, LLC
 */
@SuppressWarnings("deprecation")
public class Voice {

	public enum ERROR_CODE{
		BadAuthentication(	"Wrong username or password."),
		NotVerified(		"The account email address has not been verified. You need to access your Google account directly to resolve the issue before logging in using google-voice-java."),
		TermsNotAgreed(		"You have not agreed to terms. You need to access your Google account directly to resolve the issue before logging in using google-voice-java."),
		CaptchaRequired(	"A CAPTCHA is required. (A response with this error code will also contain an image URL and a CAPTCHA token.)"),
		Unknown(			"Unknown or unspecified error; the request contained invalid input or was malformed."),
		AccountDeleted(		"The user account has been deleted."),
		AccountDisabled(	"The user account has been disabled."),
		ServiceDisabled(	"Your access to the voice service has been disabled. (Your user account may still be valid.)"),
		ServiceUnavailable(	"The service is not available; try again later.");
		ERROR_CODE(String pLongText) {
			LONG_TEXT = pLongText;
		}
		public final String LONG_TEXT;
	}
	public boolean PRINT_TO_CONSOLE;
	/** 
	 * keeps the list of phones - lazy
	*/
	private List<PhoneOld> phoneList = null;
	private AllSettings settings;
	String general = null;
	String phonesInfo = null;
	String rnrSEE = null;
	private ERROR_CODE error;

	/**
	 * Short string identifying your application, for logging purposes. This string should take the form:
	 * "companyName-applicationName-versionID". See: http://code.google.com/apis/accounts/docs/AuthForInstalledApps.html#Request
	 */
	String source = null;
	/**
	 * User's full email address. It must include the domain (i.e. johndoe@gmail.com).
	 */
	private String user = null;
	/**
	 * User's password.
	 */
	private String pass = null;
	/**
	 * Once the login information has been successfully authenticated, Google returns a token, which your 
	 * application will reference each time it requests access to the user's account.
	 * This token must be included in all subsequent requests to the Google service for this account. 
	 * Authorization tokens should be closely guarded and should not be given to any other application, 
	 * as they represent access to the user's account. The time limit on the token varies depending on 
	 * which service issued it.
	 */
	private String authToken = null;
	/**
	 * (optional) Token representing the specific CAPTCHA challenge. Google supplies this token and the 
	 * CAPTCHA image URL in a login failed response with the error code "CaptchaRequired".
	 */
	private String captchaToken = null;
	/**
	 * Url of the image with the captcha - only filled after a captacha response to a login try
	 */
	private String captchaUrl = null;
	final static String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13";
	/**
	 * Type of account to request authorization for. Possible values are: <br/><br/>
	 * -<b>GOOGLE</b> (get authorization for a Google account only) <br/>
	 * -<b>HOSTED</b> (get authorization for a hosted account only) <br/>
	 * -<b>HOSTED_OR_GOOGLE</b> (get authorization first for a hosted account; if attempt fails, get 
	 * authorization for a Google account)<br/><br/>		
	 * Use <b>HOSTED_OR_GOOGLE</b> if you're not sure which type of account you want authorization for. 
	 * If the user information matches both a hosted and a Google account, only the hosted account is authorized.
	 */
	final static String ACCOUNT_TYPE = "GOOGLE"; //TODO Should we change this to HOSTED_OR_GOOGLE or add a custom Constructor?
	/**
	 * Name of the Google service you're requesting authorization for. Each service using the Authorization 
	 * service is assigned a name value; for example, the name associated with Google Calendar is 'cl'. 
	 * This parameter is required when accessing services based on Google Data APIs. For specific service 
	 * names, refer to the service documentation.
	 */
	final static String SERVICE = "grandcentral";
	final static String generalURLString = "https://www.google.com/voice/";
	final static String loginURLString = "https://www.google.com/accounts/ClientLogin";
	final static String inboxURLString = "https://www.google.com/voice/inbox/recent/inbox/";
	final static String starredURLString = "https://www.google.com/voice/inbox/recent/starred/";
	final static String recentAllURLString = "https://www.google.com/voice/inbox/recent/all/";
	final static String spamURLString = "https://www.google.com/voice/inbox/recent/spam/";
	final static String trashURLString = "https://www.google.com/voice/inbox/recent/spam/";
	final static String voicemailURLString = "https://www.google.com/voice/inbox/recent/voicemail/";
	final static String smsURLString = "https://www.google.com/voice/inbox/recent/sms/";
	final static String recordedURLString = "https://www.google.com/voice/inbox/recent/recorded/";
	final static String placedURLString = "https://www.google.com/voice/inbox/recent/placed/";
	final static String receivedURLString = "https://www.google.com/voice/inbox/recent/received/";
	final static String missedURLString = "https://www.google.com/voice/inbox/recent/missed/";
	final static String phoneEnableURLString = "https://www.google.com/voice/settings/editDefaultForwarding/";
	final static String generalSettingsURLString = "https://www.google.com/voice/settings/editGeneralSettings/";
	final static String phonesInfoURLString = "https://www.google.com/voice/settings/tab/phones";
	final static String groupsInfoURLString = "https://www.google.com/voice/settings/tab/groups";
	final static String voicemailInfoURLString = "https://www.google.com/voice/settings/tab/voicemailsettings";
	final static String groupsSettingsURLString = "https://www.google.com/voice/settings/editGroup/";

	/**
	 * Instantiates a new voice. This constructor is deprecated. Try
	 * Voice(String user, String pass) which automatically determines rnrSee and
	 * assigns a source.
	 * 
	 * @param user
	 *            the user
	 * @param pass
	 *            the pass
	 * @param source
	 *            the source
	 * @param rnrSee
	 *            the rnr see
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Deprecated
	public Voice(String user, String pass, String source, String rnrSee)
			throws IOException {

		this.user = user;
		this.pass = pass;
		this.rnrSEE = rnrSee;
		this.source = source;

		login();
	}

	/**
	 * A constructor which which allows a custom source.
	 * 
	 * @param user
	 *            the username in the format of user@gmail.com or user@googlemail.com
	 * @param pass
	 *            the password
	 * @param source
	 *            Short string identifying your application, for logging purposes. This string should take the form:
					"companyName-applicationName-versionID". See: http://code.google.com/apis/accounts/docs/AuthForInstalledApps.html#Request
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Voice(String user, String pass, String source) throws IOException {
		init(user, pass, source, true);

	}

	/**
	 * Instantiates a new Voice Object. This is generally the simplest and
	 * preferred constructor. This Constructor enables verbose output.
	 * 
	 * @param user
	 *            the username in the format of user@gmail.com or user@googlemail.com
	 * @param pass
	 *            the pass
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Voice(String user, String pass) throws IOException {
		init(user, pass, null, true);
	}

	/**
	 * Instantiates a new voice. Custom Source Variable allowed, and
	 * printDebugIntoSystemOut which allows for Verbose output.
	 * 
	 * @param user
	 *            the username in the format of user@gmail.com or user@googlemail.com
	 * @param pass
	 *            the password
	 * @param source
	 *            the arbitrary source identifier.  Can be anything.
	 * @param printDebugIntoToSystemOut
	 *            the print debug into to system out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Voice(String user, String pass, String source,
			boolean printDebugIntoToSystemOut) throws IOException {
		init(user, pass, source, printDebugIntoToSystemOut);
	}

	/**
	 * Internal function used by all constructors to fully initiate the Voice
	 * Object.
	 * 
	 * @param user
	 *            the username in the format of user@gmail.com or user@googlemail.com
	 * @param pass
	 *            the password for the google account
	 * @param source
	 *            the source
	 * @param printDebugIntoToSystemOut
	 *            the print debug into to system out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void init(String user, String pass, String source,
			boolean printDebugIntoToSystemOut) throws IOException {
		this.PRINT_TO_CONSOLE = printDebugIntoToSystemOut;
		this.user = user;
		this.pass = pass;
		// this.rnrSEE = rnrSee;
		if (source != null) {
			this.source = source;
		} else {
			this.source = "GoogleVoiceJava";
		}
		
		login();
		this.general = getGeneral();
		setRNRSEE();
	}
	
	/**
	 * Returns the phone list - Lazy
	 * TODO move this function in the Settings class
	 * @param refresh - set to true to force a List update from the server
	 * @return List of PhoneOld objects
	 * @throws IOException
	 */
	public List<PhoneOld> getPhoneList(boolean forceUpdate) throws IOException {
		if(phoneList==null || forceUpdate) {
			if(isLoggedIn()==false || forceUpdate) {
				login();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			
			this.phonesInfo = this.getRawPhonesInfo();
			setPhoneInfo();
		}
		return phoneList;
	}
	
	
	/**
	 * Returns the Greeting list - Lazy
	 * @param refresh - set to true to force a List update from the server
	 * @return List of Greeting objects
	 * @throws IOException
	 * @throws JSONException 
	 */
	public List<Greeting> getVoicemailList(boolean forceUpdate) throws IOException, JSONException {
		List<Greeting> lGList = new ArrayList<Greeting>();
		Greeting[] lGArray = getSettings(forceUpdate).getSettings().getGreetings();
		for (int i = 0; i < lGArray.length; i++) {
			lGList.add(lGArray[i]);
		}
		return lGList;
	}
	
	/**
	 * Returns the Group list - Lazy
	 * @param refresh - set to true to force a List update from the server
	 * @return List of Greeting objects
	 * @throws IOException
	 */
	public List<String> getGroupSettingsList(boolean forceUpdate) throws IOException {
//		return getSettings(forceUpdate).getGroupSettingsList();
//		List<String> lGList = new ArrayList<Group>();
//		String[] lGArray = getSettings(forceUpdate).getSettings().getGroups().;
//		for (int i = 0; i < lGArray.length; i++) {
//			lGList.add(lGArray[i]);
//		}
//		return lGList;
		//TODO implement getGroupSettingsList
		return null;
	}
	
	/**
	 * returns all users settings - lazy
	 * @param forceUpdate
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public AllSettings getSettings(boolean forceUpdate) throws JSONException, IOException {
		if(settings==null || forceUpdate) {
			if(isLoggedIn()==false || forceUpdate) {
				login();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			if(PRINT_TO_CONSOLE) System.out.println("Fetching Settings.");
			// remove html overhead
			String lJson = ParsingUtil.removeUninterestingParts(get(groupsInfoURLString), "<json><![CDATA[", "]]></json>", false);
			try {
				settings = new AllSettings(lJson);
			} catch (JSONException e) {
				throw new JSONException(e.getMessage()+lJson);
			}
		}
		return settings;
	}

	// public Voice(){
	// authToken = "abcde";
	// }

	/**
	 * Fetches and returns the raw page source code for the Inbox.
	 * 
	 * @return the inbox
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getInbox() throws IOException {
		return get(inboxURLString);
	}
	
	public String getInboxPage(int page) throws IOException {
		return get(inboxURLString,page);
	}

	/**
	 * Fetches the page Source Code for the Voice homepage. This file contains
	 * most of the useful information for the Google Voice Account such as
	 * attached PhoneOld info and Contacts.
	 * 
	 * @return the general
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getGeneral() throws IOException {
		return get(generalURLString);
	}
	
	public String getGeneralPage(int page) throws IOException {
		return get(generalURLString,page);
	}

	/**
	 * Gets the raw page source code for the starred items.
	 * 
	 * @return the starred item page source
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getStarred() throws IOException {
		return get(starredURLString);
	}
	
	public String getStarredPage(int page) throws IOException {
		return get(starredURLString,page);
	}

	/**
	 * Gets the raw page source code for the recent items.
	 * 
	 * @return the recent raw source code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getRecent() throws IOException {
		return get(recentAllURLString);
	}
	
	public String getRecentPage(int page) throws IOException {
		return get(recentAllURLString,page);
	}

	/**
	 * Gets the page source for the spam.
	 * 
	 * @return the spam
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getSpam() throws IOException {
		return get(spamURLString);
	}
	
	public String getSpamPage(int page) throws IOException {
		return get(spamURLString,page);
	}

	/**
	 * Gets the page source for the recorded calls.
	 * 
	 * @return the recorded
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getRecorded() throws IOException {
		return get(recordedURLString);
	}
	
	public String getRecordedPage(int page) throws IOException {
		return get(recordedURLString,page);
	}

	/**
	 * Gets the raw source code for the placed calls page.
	 * 
	 * @return the placed calls source code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getPlaced() throws IOException {
		return get(placedURLString);
	}
	
	public String getPlacedPage(int page) throws IOException {
		return get(placedURLString,page);
	}

	/**
	 * Gets the received calls source code.
	 * 
	 * @return the received
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getReceived() throws IOException {
		return get(receivedURLString);
	}
	
	public String getReceivedPage(int page) throws IOException {
		return get(receivedURLString,page);
	}

	/**
	 * Gets the missed calls source code.
	 * 
	 * @return the missed
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getMissed() throws IOException {
		return get(missedURLString);
	}
	
	public String getMissedPage(int page) throws IOException {
		return get(missedURLString,page);
	}

	/**
	 * Gets the SMS page raw source code.
	 * 
	 * @return the sMS
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getSMS() throws IOException {
		return get(smsURLString);
	}
	
	public String getSMSPage(int page) throws IOException {
		return get(smsURLString,page);
	}

	/**
	 * Internal method which parses the Homepage source code to determine the
	 * rnrsee variable, this variable is passed into most fuctions for placing
	 * calls and sms.
	 * @throws IOException 
	 */
	private void setRNRSEE() throws IOException {
		if (general != null) {
			if(general.contains("'_rnr_se': '")) {
				String p1 = general.split("'_rnr_se': '", 2)[1];
				rnrSEE = p1.split("',", 2)[0];
				p1 = null;
			} else if(general.contains("<div class=\"gc-notice\">")) {
				String gcNotice = ParsingUtil.removeUninterestingParts(general, "<div class=\"gc-notice\">", "</div>", false);	
				System.out.println(gcNotice+ "(Answer did not contain rnr_se)");
				throw new IOException(gcNotice + "(Answer did not contain rnr_se)");
			} else {
				System.out.println("Answer did not contain rnr_se! + general");
				throw new IOException("Answer did not contain rnr_se! + general");
			}
		} else {
			System.out.println("setRNRSEE(): Answer was null! + general");
			throw new IOException("setRNRSEE(): Answer was null! + general");
		}
	}

	//TODO Combine with or replace setPhoneInfo
	public String getRawPhonesInfo() throws IOException{
		return get(phonesInfoURLString);
	}
	
	

	/**
	 * Reads raw account info, and creates the phoneList of PhoneOld objects.
	 */
	private void setPhoneInfo() {
		if (general != null && phonesInfo!=null) {
			
			List<Integer> disabledList = new ArrayList<Integer>();
			
			String f1 = phonesInfo.substring(phonesInfo.indexOf("disabledIdMap\":{"));
			f1=f1.substring(16);
			f1 = f1.substring(0,f1.indexOf("},\""));
			String[] rawslice = f1.split(",");
			
			for(int i=0;i<rawslice.length;i++){
				if(rawslice[i].contains("true")){
					//System.out.println(rawslice[i]);
					rawslice[i] = rawslice[i].replace("\":true", "");
					rawslice[i] =rawslice[i].replace("\"", "");
					//System.out.println(rawslice[i]);
					Integer z = Integer.parseInt(rawslice[i]);
					disabledList.add(z);
				}
			}
			
			
			
			List<PhoneOld> phoneList = new ArrayList<PhoneOld>();
			String p1 = general.split("'phones':", 2)[1];
			p1 = (p1.split("'_rnr_se'", 2))[0];
			String[] a = p1.split("\\{\"id\"\\:");
			// if(PRINT_TO_CONSOLE) System.out.println(a[0]);
			for (int i = 1; i < a.length; i++) {
				//PhoneOld phone = new PhoneOld();
				String[] b = a[i].split(",\"wd\"\\:\\{", 2)[0].split(",");
				//phone.id = Integer.parseInt(b[0].replaceAll("\"", ""));
				int id = Integer.parseInt(b[0].replaceAll("\"", ""));
				String number = "";
				String type = "";
				String name = "";
				String formattedNumber="";
				String carrier = "";
				Boolean verified = false;
				for (int j = 0; j < b.length; j++) {
					if (b[j].contains("phoneNumber")) {
						//phone.number = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						number  = b[j].split("\\:")[1]
						   .replaceAll("\"", "");
					} else if (b[j].contains("type")) {
						//phone.type = b[j].split("\\:")[1].replaceAll("\"", "");
						type = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("name")) {
						//phone.name = b[j].split("\\:")[1].replaceAll("\"", "");
						name = b[j].split("\\:")[1].replaceAll("\"", "");
					} else if (b[j].contains("formattedNumber")) {
						//phone.formattedNumber = b[j].split("\\:")[1]
						//		.replaceAll("\"", "");
						formattedNumber = b[j].split("\\:")[1]
						    								.replaceAll("\"", "");
					} else if (b[j].contains("carrier")) {
						//phone.carrier = b[j].split("\\:")[1].replaceAll("\"",
						//		"");
						carrier = b[j].split("\\:")[1].replaceAll("\"",
							"");
					} else if (b[j].contains("\"verified")) {
						//phone.verified = Boolean
						//		.parseBoolean(b[j].split("\\:")[1].replaceAll(
						//				"\"", ""));
						verified = Boolean
						.parseBoolean(b[j].split("\\:")[1].replaceAll(
								"\"", ""));
					}
				}
				if(id!=0 && !number.equals("")&&!formattedNumber.equals("")&&!type.equals("")&&!name.equals("")){
					
					boolean enabled;
					
					if(disabledList.contains(new Integer(id))){
						enabled = false;
					}else{
						enabled = true;
					}
					
					PhoneOld phoneOld = new PhoneOld(id, number, formattedNumber, type, name, carrier, verified,enabled);
											
					phoneList.add(phoneOld);
				}else{
					System.out.println("Error in phone object creation.");
				}
			}

			this.phoneList = phoneList;

		}
	}

	/**
	 * Place a call.
	 * 
	 * @param originNumber
	 *            the origin number
	 * @param destinationNumber
	 *            the destination number
	 * @param phoneType
	 *            the phone type, this is a number such as 1,2,7 formatted as a String
	 * @return the raw response string received from Google Voice.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String call(String originNumber, String destinationNumber,
			String phoneType) throws IOException {
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
		callconn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

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

	/**
	 * Cancel a call that was just placed.
	 * 
	 * @param originNumber
	 *            the origin number
	 * @param destinationNumber
	 *            the destination number
	 * @param phoneType
	 *            the phone type
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String cancelCall(String originNumber, String destinationNumber,
			String phoneType) throws IOException {
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
		callconn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

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
	
	/** 
	 * Enables multiple phones in one post 
	 *  
	 * TODO Test this with multiple phones in an account
	 * 		Best would be to be able to construct a url which can switch multiple phones at a time
	 * 
	 * @param IDs Array of Phones to enable
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void phonesEnable(int[] IDs) throws IOException {

		if(IDs.length<1) {
			return;
		} else if(IDs.length==1) {
			//launch single (no thread overhead)	
			phoneEnable(IDs[0]);
		} else {
			for (int i = 0; i < IDs.length; i++) {
				//TODO spawn threads!
				int j = IDs[i];
				String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
				paraString += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
						+ URLEncoder.encode("1", "UTF-8");
				paraString += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
						+ URLEncoder.encode(Integer.toString(j), "UTF-8");
				paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
					+ URLEncoder.encode(rnrSEE, "UTF-8");
			
				phonesEnableDisableApply(paraString);
			}
		}
		
	}
	
	/**
	 * Enables one of the the phones attached to the account from ringing.
	 * Requires the internal ID for that phone, as an integer, usually 1,2,3,
	 * etc.
	 * 
	 * @param ID
	 *            the iD
	 * @return the raw response of the enable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String phoneEnable(int ID) throws IOException {
		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		paraString += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8");
		paraString += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(ID), "UTF-8");
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		return phonesEnableDisableApply(paraString);
	}
	
	/** 
	 * Disables multiple phones in one post
	 * 
	 * TODO Test this with multiple phones in an account
	 * 		Make faster - spawn threads
	 *      Best would be to be able to construct a url which can switch multiple phones at a time
	 * 
	 * @param IDs Array of Phones to disable
	 * @return the raw response of the disable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void phonesDisable(int[] IDs) throws IOException {
		
		if(IDs.length<1) {
			return;
		} else if(IDs.length==1) {
			//launch single (no thread overhead)	
			phoneDisable(IDs[0]);
		} else {
			for (int i = 0; i < IDs.length; i++) {
				//TODO spawn threads!
				int j = IDs[i];
				String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
				paraString += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
						+ URLEncoder.encode("0", "UTF-8");
				paraString += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
						+ URLEncoder.encode(Integer.toString(j), "UTF-8");
				paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
					+ URLEncoder.encode(rnrSEE, "UTF-8");
			
				phonesEnableDisableApply(paraString);
			}
		}

	}

	/**
	 * Disable one of the the phones attached to the account from ringing.
	 * Requires the internal ID for that phone, as an integer, usually 1,2,3,
	 * etc.
	 * 
	 * @param ID
	 *            the iD
	 * @return the raw response of the disable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String phoneDisable(int ID) throws IOException {
		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		paraString += "&" + URLEncoder.encode("enabled", "UTF-8") + "="
				+ URLEncoder.encode("0", "UTF-8");
		paraString += "&" + URLEncoder.encode("phoneId", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(ID), "UTF-8");
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		return phonesEnableDisableApply(paraString);
	}

	/**
	 * Executes the enable/disable action with the provided url params
	 * 
	 * @param paraString
	 *            the URL Parameters (encoded), ie ?auth=3248sdf7234&enable=0&phoneId=1&enable=1&phoneId=2&_rnr_se=734682ghdsf
	 * @return the raw response of the disable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String phonesEnableDisableApply(String paraString) throws IOException {
		String out = "";

		
		// POST /voice/call/connect/ outgoingNumber=[number to
		// call]&forwardingNumber=[forwarding
		// number]&subscriberNumber=undefined&remember=0&_rnr_se=[pull from
		// page]

		//
		URL requestURL = new URL(phoneEnableURLString);

		URLConnection conn = requestURL.openConnection();
		conn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

		conn.setDoOutput(true);
		conn.setDoInput(true);

		OutputStreamWriter callwr = new OutputStreamWriter(conn
				.getOutputStream());
		callwr.write(paraString);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

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
	
	/**
	 * Enables/disables the call Announcement setting (general for all phones)
	 * 
	 * @param announceCaller <br/>
	 *            true Announces caller's name and gives answering options <br/>
	 *            false Directly connects calls when phones are answered
	 * @return the raw response of the disable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String setCallPresentation(boolean announceCaller) throws IOException {
		String out = "";

		URL requestURL = new URL(generalSettingsURLString);
		/** 0 for enable, 1 for disable **/
		String announceCallerStr="";

		if(announceCaller) {
			announceCallerStr = "0";
			if (PRINT_TO_CONSOLE) System.out.println("Turning caller announcement on.");
		}
		else {
			announceCallerStr = "1";
			if (PRINT_TO_CONSOLE) System.out.println("Turning caller announcement off.");
		}
		
		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		paraString += "&" + URLEncoder.encode("directConnect", "UTF-8") + "="
				+ URLEncoder.encode(announceCallerStr, "UTF-8");
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");


		URLConnection conn = requestURL.openConnection();
		conn.setRequestProperty("User-agent",
								USER_AGENT);

		conn.setDoOutput(true);
		conn.setDoInput(true);

		OutputStreamWriter callwr = new OutputStreamWriter(conn.getOutputStream());
		callwr.write(paraString);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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
	
	/**
	 * This is the general voicemail greeting callers hear
	 * 
	 * @param greetingToSet <br/>
	 *            number of the greeting to choose
	 * @return the raw response of the disable action.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String setVoicemailGreetingId(String greetingToSet) throws IOException {

		URL requestURL = new URL(generalSettingsURLString);

		if (PRINT_TO_CONSOLE) System.out.println("Activating Greeting#"+greetingToSet);

		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		paraString += "&" + URLEncoder.encode("greetingId", "UTF-8") + "="
				+ URLEncoder.encode(greetingToSet+"", "UTF-8");
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");


		return postSettings(requestURL, paraString);
	}
	
	/**
	 * Activated or deactivated the Do Not disturb function.<br>
	 * Enable this to send to voicemail all calls made to your Google number.
	 * @param dndEnabled true to enable dnd, false to disable it
	 * @return
	 * @throws IOException
	 */
	public String setDoNotDisturb(boolean dndEnabled) throws IOException {

		URL requestURL = new URL(generalSettingsURLString);
		
		String enabled;

		if(dndEnabled) {
			if (PRINT_TO_CONSOLE) System.out.println("Enabling dnd");
			enabled = "1";
		} else {
			if (PRINT_TO_CONSOLE) System.out.println("Disabling dnd");
			enabled = "0";
		}

		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");
		paraString += "&" + URLEncoder.encode("doNotDisturb", "UTF-8") + "="
				+ URLEncoder.encode(enabled+"", "UTF-8");
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");


		return postSettings(requestURL, paraString);
	}
	
	/**
	 * Applies the settings for this group
	 * @param group 
	 * @return
	 * @throws IOException
	 */
	public String setNewGroupSettings(Group group) throws IOException {
		URL requestURL = new URL(groupsSettingsURLString);

		String paraString = URLEncoder.encode("auth", "UTF-8") + "="
			+ URLEncoder.encode(authToken, "UTF-8");
		
		// 1=true 0=false 
		int isCustomGreeting = 0;
		if(group.isCustomGreeting()) {
			isCustomGreeting = 1;
		}
		paraString += "&" + URLEncoder.encode("isCustomGreeting", "UTF-8") + "="
			+ URLEncoder.encode(isCustomGreeting+"", "UTF-8");
		
		int greetingId = group.getGreetingId();
		paraString += "&" + URLEncoder.encode("greetingId", "UTF-8") + "="
			+ URLEncoder.encode(greetingId+"", "UTF-8");
		
		for (int i = 0; i < group.getDisabledForwardingIds().size(); i++) {
			paraString += "&" + URLEncoder.encode("disabledPhoneIds", "UTF-8") + "="
				+ URLEncoder.encode(group.getDisabledForwardingIds().get(i).getId(), "UTF-8");
		}
		
		int directConnect = 0;
		if(group.isDirectConnect()) {
			directConnect = 1;
		}
		paraString += "&" + URLEncoder.encode("directConnect", "UTF-8") + "="
			+ URLEncoder.encode(directConnect+"", "UTF-8");
		
		int isCustomDirectConnect = 0;
		if(group.isCustomDirectConnect()) {
			isCustomDirectConnect = 1;
		}
		paraString += "&" + URLEncoder.encode("isCustomDirectConnect", "UTF-8") + "="
			+ URLEncoder.encode(isCustomDirectConnect+"", "UTF-8");
		
		int isCustomForwarding = 0;
		if(group.isCustomForwarding()) {
			isCustomForwarding = 1;
		}
		paraString += "&" + URLEncoder.encode("isCustomForwarding", "UTF-8") + "="
			+ URLEncoder.encode(isCustomForwarding+"", "UTF-8");
		
		paraString += "&" + URLEncoder.encode("id", "UTF-8") + "="
			+ URLEncoder.encode(group.getId(), "UTF-8");
		
		paraString += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");

		return postSettings(requestURL, paraString);
	}

	/**
	 * Posts a settings change
	 * 
	 * @param requestURL
	 * @param paraString
	 * @return
	 * @throws IOException
	 */
	private String postSettings(URL requestURL, String paraString)
			throws IOException {
		String out = "";
		URLConnection conn = requestURL.openConnection();
		conn.setRequestProperty("User-agent",
								USER_AGENT);

		conn.setDoOutput(true);
		conn.setDoInput(true);

		OutputStreamWriter callwr = new OutputStreamWriter(conn.getOutputStream());
		callwr.write(paraString);
		callwr.flush();

		BufferedReader callrd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line;
		while ((line = callrd.readLine()) != null) {
			out += line + "\n\r";
		}

		callwr.close();
		callrd.close();

		if (out.equals("")) {
			throw new IOException("No Response Data Received.");
		}
		
		if(PRINT_TO_CONSOLE) System.out.println(out);

		return out;
	}

	/**
	 * Send an SMS
	 * 
	 * @param destinationNumber
	 *            the destination number
	 * @param txt
	 *            the Text of the message. Messages longer than the allowed
	 *            character length will be split into multiple messages.
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String sendSMS(String destinationNumber, String txt)
			throws IOException {
		String out = "";
		String smsdata = URLEncoder.encode("auth", "UTF-8") + "="
				+ URLEncoder.encode(authToken, "UTF-8");

		smsdata += "&" + URLEncoder.encode("phoneNumber", "UTF-8") + "="
				+ URLEncoder.encode(destinationNumber, "UTF-8");
		smsdata += "&" + URLEncoder.encode("text", "UTF-8") + "="
				+ URLEncoder.encode(txt, "UTF-8");
		smsdata += "&" + URLEncoder.encode("_rnr_se", "UTF-8") + "="
				+ URLEncoder.encode(rnrSEE, "UTF-8");
		URL smsurl = new URL("https://www.google.com/voice/sms/send/");

		URLConnection smsconn = smsurl.openConnection();
		smsconn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

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
	
	/*
	 * TODO: REMOVE before release
	 */
	public String getONLYFORTEST(String urlString) throws IOException {
		return get(urlString);
	}

	/**
	 * HTML GET request for a given URL String.
	 * 
	 * @param urlString
	 *            the url string
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	String get(String urlString) throws IOException {
		URL url = new URL(urlString + "?auth="
				+ URLEncoder.encode(authToken, "UTF-8"));
		URLConnection conn = url.openConnection();
		conn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line + "\n\r");
		}
		rd.close();
		String result = sb.toString();

		return result;
	}
	
	
	/**
	 * HTML GET request for a given URL String and a given page number
	 * 
	 * 
	 * @param urlString the url string
	 * @param page number must be a natural number
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String get(String urlString,int page) throws IOException {
		URL url = new URL(urlString + "?page=p"+page+"&auth="
				+ URLEncoder.encode(authToken, "UTF-8")
				);
		//url+="&page="+page;
		URLConnection conn = url.openConnection();
		conn
				.setRequestProperty(
						"User-agent",
						USER_AGENT);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line + "\n\r");
		}
		rd.close();
		String result = sb.toString();

		return result;
	}
	
	/**
	 * Login Method to refresh authentication with Google Voice.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void login()  throws IOException {
		login(null);
	}
	
	/**
	 * Use this login method to login - use captchaAnswer to answer a captcha challenge
	 * @param captchaAnswer - (optional) String entered by the user as an answer to a CAPTCHA challenge. - null to make a normal login attempt
	 * @throws IOException
	 */
	public void login(String captchaAnswer) throws IOException {

		String data = URLEncoder.encode("accountType", "UTF-8") + "="
				+ URLEncoder.encode(ACCOUNT_TYPE, "UTF-8");
		data += "&" + URLEncoder.encode("Email", "UTF-8") + "="
				+ URLEncoder.encode(user, "UTF-8");
		data += "&" + URLEncoder.encode("Passwd", "UTF-8") + "="
				+ URLEncoder.encode(pass, "UTF-8");
		data += "&" + URLEncoder.encode("service", "UTF-8") + "="
				+ URLEncoder.encode(SERVICE, "UTF-8");
		data += "&" + URLEncoder.encode("source", "UTF-8") + "="
				+ URLEncoder.encode(source, "UTF-8");
		if(captchaAnswer!=null && captchaToken!=null) {
			data += "&" + URLEncoder.encode("logintoken", "UTF-8") + "="
					+ URLEncoder.encode(captchaToken, "UTF-8");
			data += "&" + URLEncoder.encode("logincaptcha", "UTF-8") + "="
					+ URLEncoder.encode(captchaAnswer, "UTF-8");
		}

		// Send data
		URL url = new URL(loginURLString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		int responseCode = conn.getResponseCode();
		InputStream is;
		if(responseCode==200) {
			is = conn.getInputStream();
		} else {
			is = conn.getErrorStream();
		}
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader rd = new BufferedReader(isr);
		String line;
		
		/*
		 * A failure response contains an error code and a URL to an error page that can be displayed to the user. 
		 * If the error code is a CAPTCHA challenge, the response also includes a URL to a CAPTCHA image and a special 
		 * token. Your application should be able to solicit an answer from the user and then retry the login request. 
		 * To display the CAPTCHA image to the user, prefix the CaptchaUrl value with "http://www.google.com/accounts/", 
		 * for example: " http://www.google.com/accounts/Captcha?ctoken=HiteT4b0Bk5Xg18_AcVoP6-yFkHPibe7O9EqxeiI7lUSN".
		 */
		String lErrorString = "Unknown Connection Error."; // ex: Error=CaptchaRequired
		String lUrl = ""; // ex: Url=http://www.google.com/login/captcha
		String lCaptchaToken = ""; // ex: CaptchaToken=DQAAAGgA...dkI1LK9
		String lCaptchaUrl = ""; // ex: CaptchaUrl=Captcha?ctoken=HiteT4b0Bk5Xg18_AcVoP6-yFkHPibe7O9EqxeiI7lUSN

		// String AuthToken = null;
		while ((line = rd.readLine()) != null) {
			// if(PRINT_TO_CONSOLE) System.out.println(line);
			if (line.contains("Auth=")) {
				this.authToken = line.split("=", 2)[1].trim();
				if (PRINT_TO_CONSOLE)
					System.out.println("Login success - auth token received.");
			} else if (line.contains("Error=")) {
				lErrorString = line.split("=", 2)[1].trim();
				error = getErrorEnumByCode(lErrorString);
				if (PRINT_TO_CONSOLE)
					System.out.println("Login error - "+lErrorString);
			} else if (line.contains("Url=")) {
				lUrl = line.split("=", 2)[1].trim();
			} else if (line.contains("CaptchaToken=")) {
				captchaToken = line.split("=", 2)[1].trim();
			} else if (line.contains("CaptchaUrl=")) {
				captchaUrl = "http://www.google.com/accounts/" + line.split("=", 2)[1].trim();
			}
		}
		wr.close();
		rd.close();

		if (this.authToken == null) {
			
			throw new IOException(lErrorString + " " + error.LONG_TEXT);
		}
	}

	/**
	 * @return
	 */
	private ERROR_CODE getErrorEnumByCode(String pErrorCodeString) {
		if(pErrorCodeString.equals(ERROR_CODE.AccountDeleted.name())) {
			return ERROR_CODE.AccountDeleted;
		} else if(pErrorCodeString.equals(ERROR_CODE.AccountDisabled.name())) {
			return ERROR_CODE.AccountDisabled;
		} else if(pErrorCodeString.equals(ERROR_CODE.BadAuthentication.name())) {
			return ERROR_CODE.BadAuthentication;
		} else if(pErrorCodeString.equals(ERROR_CODE.CaptchaRequired.name())) {
			return ERROR_CODE.CaptchaRequired;
		} else if(pErrorCodeString.equals(ERROR_CODE.NotVerified.name())) {
			return ERROR_CODE.NotVerified;
		} else if(pErrorCodeString.equals(ERROR_CODE.ServiceDisabled.name())) {
			return ERROR_CODE.ServiceDisabled;
		} else if(pErrorCodeString.equals(ERROR_CODE.TermsNotAgreed.name())) {
			return ERROR_CODE.TermsNotAgreed;
		} else {
			return ERROR_CODE.Unknown;
		}
	}
	
	public ERROR_CODE getError() {
		return error;
	}
	
	public String getCaptchaUrl() {
		return captchaUrl;
	}
	
	public String getCaptchaToken() {
		return captchaToken;
	}

	/**
	 * Fires a Get request for Recent Items. If the Response requests login
	 * authentication or if an exception is thrown, a false is returned,
	 * otherwise if arbitrary text is contained for a logged in account, a true
	 * is returned.
	 * 
	 *TODO Examine methodology.
	 * 
	 * @return true, if is logged in
	 */
	public boolean isLoggedIn() {
		String res;
		try {
			res = getRecent();
		} catch (IOException e) {
			return false;
		}
		if (res
				.contains("<meta name=\"description\" content=\"Google Voice gives you one number")
				&& res
						.contains("action=\"https://www.google.com/accounts/ServiceLoginAuth?service="+SERVICE+"\"")) {
			return false;
		} else {
			if (res.contains("Enter a new or existing contact name")
					|| res.contains("<json><![CDATA[")) {
				return true;
			}
		}
		return false;
	}

	
}
