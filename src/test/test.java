package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.techventus.server.voice.Voice;

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
	      System.out.println("Enter A \"Source\" for the Log:");
	      String source = null;
	      try {
	    	  source = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
	      
	      try {
			Voice voice = new Voice(userName, pass, source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      
	      
	}
}
