package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.Group;
import com.techventus.server.voice.datatypes.Phone;
import com.techventus.server.voice.datatypes.Greeting;

public class test {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String userName = null;
	static String pass = null;
	static Properties testProps = null;
	private static Voice voice;
	
	public static void main(String[] args){
		
		try {
			testProps = load("test/privateTestData.properties");
			userName = testProps.getProperty("username");
			pass = testProps.getProperty("password");
		} catch (Exception e) {
			System.out.println("Could not read the testProps, falling back to input. ("+e.getMessage()+")");
			System.out.println("Enter Your Google Voice Username, eg user@gmail.com:");

			try {
				userName = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error trying to read your name!");
				System.exit(1);
			}

			System.out.println("Enter Your Password:");


			try {
				pass = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error trying to read your name!");
				System.exit(1);
			}
		}
		
		try {
			voice = new Voice(userName, pass);
		} catch (IOException e) {
			System.out.println("IO error creating voice!"+e.getLocalizedMessage());
		}

		listTests();

		/*
	      System.out.println("Enter A \"Source\" for the Log:");
	      String source = null;
	      try {
	    	  source = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }

	      System.out.println("Log into Google Voice and find the _rnr_se variable in the page Source. ");
	      System.out.println("Enter rnr_se_ value:");
	      String rnrSee = null;
	      try {
	    	  rnrSee = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
		 */


	}

	/**
	 * Lets the Tester choose the Test to run
	 */
	private static void listTests() {
		System.out.println("Availible Tests for "+userName);
		System.out.println("0: Exit");  
		System.out.println("1: Multi phone enable / disable");
		System.out.println("2: Inbox paging");
		System.out.println("3: Call Announcement Settings (Presentation)");
		System.out.println("4: Set Default Voicemail Greeting");
		System.out.println("5: Change do not disturb setting.");
		System.out.println("6: Change Group settings.");
		System.out.println("7: Read all settings and print them (cached)");
		System.out.println("8: Read all settings and print them (uncached)");

		int testNr = 0;
		try {
			testNr = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			System.out.println("Error trying to read the testNr!"+e.getMessage());
			System.exit(1);
		}
		  
		runTest(userName, pass, testNr);
	}

	/**
	 * @param userName
	 * @param pass
	 * @param testNr
	 */
	private static void runTest(String userName, String pass, int testNr) {
	    if(testNr==0) {// 0: Exit							
			System.out.println("Exiting.");
			System.exit(1);
	    }
		try {
	    	  
			
			//Voice voice = new Voice();
//			try {
				System.out.println(voice.isLoggedIn());
				//Thread.sleep(2000);

					switch (testNr) {
						case 1: // 1: Multi phone enable / disable 
							System.out.println("******** Starting Test "+testNr+" ********");
							// create int Array from all phone ids
							int[] phonesToChangeStatus = new int[voice.getPhoneList(true).size()];
							int i=0;
							
							for (Iterator<Phone> iterator = voice.getPhoneList(false).iterator(); iterator.hasNext();) {
								Phone type = (Phone) iterator.next();
								phonesToChangeStatus[i] = type.id;
								i++;
							}
							
							System.out.println("Current phone status:");
							for (Iterator<Phone> iterator = voice.getPhoneList(true).iterator(); iterator.hasNext();) {
								Phone type = (Phone) iterator.next();
								System.out.println(type.toString());
							}
							
							//Disable all phones with one call
							voice.phonesDisable(phonesToChangeStatus);
							
							// Output
							System.out.println("After deactivate multi:");
							for (Iterator<Phone> iterator = voice.getPhoneList(true).iterator(); iterator.hasNext();) {
								Phone type = (Phone) iterator.next();
								System.out.println(type.toString());
							}
							
							//Enable all phones with one call
							voice.phonesEnable(phonesToChangeStatus);
							
							// Output
							System.out.println("After activate multi:");
							for (Iterator<Phone> iterator = voice.getPhoneList(false).iterator(); iterator.hasNext();) {
								Phone type = (Phone) iterator.next();
								System.out.println(type.toString());
							}
							
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 2: // 2: Inbox paging
							System.out.println("******** Starting Test "+testNr+" ********");
//							Thread.sleep(2000);
							System.out.println(voice.getInboxPage(1000));
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 3: // 3: Call Announcement settings (Presentation)
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("Type 'true' for enable, 'false' for disable");
							boolean choice = false;
							try {
								choice = Boolean.parseBoolean(br.readLine());
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
//							Thread.sleep(2000);
							System.out.println(voice.setCallPresentation(choice));
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 4: // 4: Caller ID in
							System.out.println("******** Starting Test "+testNr+" ********");
							for (Iterator<Greeting> iterator = voice.getVoicemailList(true).iterator(); iterator.hasNext();) {
								Greeting type = (Greeting) iterator.next();
								System.out.println(type.toString());
							}
							System.out.println("Choose the id of the voicemail greeting to use. ie '0' system standard or '1','2'");
							String voicemailNr = "0";
							try {
								voicemailNr = br.readLine();
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
//							Thread.sleep(2000);
							voice.setVoicemailGreetingId(voicemailNr);
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 5: // 5: Do not disturb
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("Type 'true' for enable, 'false' for disable");
							boolean dndChoice = false;
							try {
								dndChoice = Boolean.parseBoolean(br.readLine());
							} catch (Exception e) {
								System.out.println("Error trying to read the choice!"+e.getMessage());
								System.exit(1);
							}
							voice.setDoNotDisturb(dndChoice);
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 6: // 6: Group settings
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println("All to json:"+Group.listToJson(voice.getSettings(false).getGroupSettingsList()));
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 7: // 7: Read all settings - cached
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println(voice.getSettings(false).toJson());
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 8: // 8: Read all settings - uncached
							System.out.println("******** Starting Test "+testNr+" ********");
							System.out.println(voice.getSettings(true).toJson());
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
	
						default: 						
							System.out.println("Test "+testNr+" not found, exiting.");
							System.exit(1);
							break;
					}
					
					
//				Thread.sleep(2000);
				
				//System.out.println(voice.getSMS());
				//System.out.println(voice.getInbox());
//				System.out.println(voice.getInboxPage(1000));
				//System.out.println(voice.getInboxPage(100));
				/*
				System.out.println(voice.getInbox());
				Thread.sleep(2000);
				System.out.println(voice.phoneDisable(6));
				
				
				System.out.println(voice.getInbox());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getMissed());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getPlaced());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getReceived());
				Thread.sleep(2000);
				System.out.println("**********************************");
				
				System.out.println("**********************************");
				System.out.println(voice.getRecorded());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getSMS());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getSpam());
				Thread.sleep(2000);
				System.out.println("**********************************");
				System.out.println(voice.getStarred());
				Thread.sleep(2000);
				System.out.println("**********************************");
				*/
		
//			} catch (InterruptedException e) {			
//				e.printStackTrace();
//			}		
		} catch (IOException e) {	
			e.printStackTrace();
		}
		listTests(); // List the Tests again
	}
	
	/**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    private static Properties load(String propsFile) throws IOException {
//        Properties props = new Properties();
//
//        ResourceBundle.getBundle(propsFile);
//        final ResourceBundle rb = ResourceBundle.getBundle(propsFile, Locale.getDefault (), ClassLoader.getSystemClassLoader());
//        for (Enumeration keys = rb.getKeys (); keys.hasMoreElements ();)
//        {
//            final String key = (String) keys.nextElement ();
//            final String value = rb.getString (key);
//            
//            props.put (key, value);
//        } 
//        return props;
    	
    	Properties result = null;
        InputStream in = null;
         
         if (! propsFile.endsWith (".properties"))
        	 propsFile = propsFile.concat (".properties");
                         
         // Returns null on lookup failures:
         in = ClassLoader.getSystemClassLoader().getResourceAsStream (propsFile);
         if (in != null)
         {
             result = new Properties ();
             result.load (in); // Can throw IOException
         }
         testProps = result;
         return result;
    }
	
}
