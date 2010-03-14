package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.Phone;

public class test {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String userName = null;
	static String pass = null;
	static Properties testProps = null;
	
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
	    	  
			Voice voice = new Voice(userName, pass);
			//Voice voice = new Voice();
			try {
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
							if(voice.getPhoneList(false)!=null && voice.getPhoneList(false).size()>0) {
								for(int ii=0;ii<voice.getPhoneList(false).size();ii++){
									System.out.println(voice.getPhoneList(false).get(ii).toString());
								}
							}
							
							//Disable all phones with one call
							voice.phonesDisable(phonesToChangeStatus);
							
							// Output
							System.out.println("After deactivate multi:");
							voice.getPhoneList(true);
							for(int ii=0;i<voice.getPhoneList(false).size();ii++){
								System.out.println(voice.getPhoneList(false).get(ii).toString());
							}
							
							//Enable all phones with one call
							voice.phonesEnable(phonesToChangeStatus);
							
							// Output
							System.out.println("After activate multi:");
							voice.getPhoneList(true);
							for(int ii=0;i<voice.getPhoneList(false).size();ii++){
								System.out.println(voice.getPhoneList(false).get(ii).toString());
							}
							
							System.out.println("******** Finished Test "+testNr+" ********");
							break;
							
						case 2: // 2: Inbox paging
							System.out.println("******** Starting Test "+testNr+" ********");
							Thread.sleep(2000);
							System.out.println(voice.getInboxPage(1000));
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
		
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}		
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
