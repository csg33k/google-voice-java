package com.techventus.server.voice.datatypes.records;

import com.techventus.server.voice.datatypes.Contact;

import java.util.Date;


/**
 * @author Tobias Eisentraeger
 *
 */
public class Call extends Record {

	/**
	 * @param title
	 * @param date
	 * @param contact
	 */
	public Call(String id, String title, Date date, Contact contact) {
		super(id, title, date, contact);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String ret = "";
		if(id!=null){
			ret+="id="+id+";";
		}
		if(title!=null){
			ret+="title="+title+";";
		}
		if(date!=null){
			ret+="date="+date+";";
		}
		if(contact!=null){
			ret+="contact="+contact+";";
		}
		return ret;
	}

	
}
