package com.techventus.server.voice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.techventus.server.voice.TransscriptElement.RecognitionLevel;

/**
 * A Transscript of a voicemail
 * @author Tobias Eisentraeger
 *
 */
public class Transscript {
	private List<TransscriptElement> elements;

	public Transscript(List<TransscriptElement> elements) {
		super();
		this.elements = elements;
	}
	
	public Transscript(String simpleTransscript) {
		super();
		this.elements = new ArrayList<TransscriptElement>();
		elements.add(new TransscriptElement(simpleTransscript, null, RecognitionLevel.UNKNOWN));
	}

	public String toString() {
		String ret="";
		for (Iterator<TransscriptElement> iter = elements.iterator(); iter.hasNext();) {
			TransscriptElement element = (TransscriptElement) iter.next();
			if(iter.hasNext()) {
				ret = ret + element.getText() + " ";
			} else {
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
