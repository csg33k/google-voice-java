package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.Phone;

public class test {
	public static void main(String[] args){
		System.out.println("Enter Your Google Voice Username, eg user@gmail.com:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	      String userName = null;
	      try {
	         userName = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
	      
	      System.out.println("Enter Your Password:");
	      
	      String pass = null;
	      try {
	         pass = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
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
	      //test
	      try {
	    	  
			Voice voice = new Voice(userName, pass);
			//Voice voice = new Voice();
			try {
				System.out.println(voice.isLoggedIn());
				//Thread.sleep(2000);
				if(voice.phoneList!=null && voice.phoneList.size()>0) {
					for(int i=0;i<voice.phoneList.size();i++){
						System.out.println(voice.phoneList.get(i).toString());
					}
					Thread.sleep(2000);
					
					// create int Array from all phone ids
					int[] phonesToChangeStatus = new int[voice.phoneList.size()];
					int i=0;
					
					for (Iterator<Phone> iterator = voice.phoneList.iterator(); iterator
							.hasNext();) {
						Phone type = (Phone) iterator.next();
						phonesToChangeStatus[i] = type.id;
						i++;
					}
					
					//Disable all phones with one call
					voice.phonesDisable(phonesToChangeStatus);
					
					// Output
					System.out.println("After deactivate multi.");
					voice = new Voice(userName, pass);
					for(int ii=0;i<voice.phoneList.size();ii++){
						System.out.println(voice.phoneList.get(ii).toString());
					}
					
					//Enable all phones with one call
					voice.phonesEnable(phonesToChangeStatus);
					
					// Output
					System.out.println("After activate multi.");
					voice = new Voice(userName, pass);
					for(int ii=0;i<voice.phoneList.size();ii++){
						System.out.println(voice.phoneList.get(ii).toString());
					}
				}
				Thread.sleep(2000);
				
				//System.out.println(voice.getSMS());
				//System.out.println(voice.getInbox());
				System.out.println(voice.getInboxPage(1000));
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
	      
	      
	      
	}
}
