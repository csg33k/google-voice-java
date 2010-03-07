package com.techventus.server.voice.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.techventus.server.voice.datatypes.TransscriptElement.RecognitionLevel;

/**
 * A Transscript of a voicemail
 * @author Tobias Eisentraeger
 *
 */
public class Transscript {
	private List<TransscriptElement> elements;

	/**
	 * Creates a Transscript out of a List of Elements - usage:
	 * 
	    List<TransscriptElement> transElementList = new ArrayList<TransscriptElement>();
		// go through the array and create transscript elments
		for (int i = 0; i < transElementsAsString.length; i++) {
			transElementList.add(TransscriptElement.extractTransscriptElement("<span"+transElementsAsString[i]));
		}
		trans = new Transscript(transElementList);
	 * 
	 * @param elements
	 */
	public Transscript(List<TransscriptElement> elements) {
		super();
		this.elements = elements;
	}
	/**
	 * Creates a transcripts with only Text, with RecognitionLevel.UNKNOWN
	 * @param simpleTransscript
	 */
	public Transscript(String simpleTransscript) {
		super();
		this.elements = new ArrayList<TransscriptElement>();
		elements.add(new TransscriptElement(simpleTransscript, null, RecognitionLevel.UNKNOWN));
	}

	/**
	 * returns a simple String representation
	 */
	public String toString() {
		String ret="";
		for (Iterator<TransscriptElement> iter = elements.iterator(); iter.hasNext();) {
			TransscriptElement element = (TransscriptElement) iter.next();
			if(iter.hasNext()&&element!=null) {
				ret = ret + element.getText() + " ";
			} else if(element!=null) {
				ret = ret + element.getText();
			}
		}
		return ret;
	}
	
	/**
	 * @return the elements
	 */
	public List<TransscriptElement> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(List<TransscriptElement> elements) {
		this.elements = elements;
	}
	
	
}
