package com.techventus.server.voice;

/**
 * @author Tobias Eisentraeger
 *
 */
public class TransscriptElement {
	private String text;
	private String id;
	private RecognitionLevel level;
	public enum RecognitionLevel {
		MED1 ("med1"),
		MED2 ("med2"),
		HIGH ("high"),
		UNKNOWN ("unknown");
		private final String text;
		RecognitionLevel(String text) {
			this.text = text;
		}
		public String getText() {
			return text;
		}
	}
	
	public TransscriptElement(String text, String id, RecognitionLevel level) {
		this.text = text;
		this.id = id;
		this.level = level;
	}
	
	/**
	 * Creates the element based on the html, for example:
	 * <span id="0-33" class="gc-word-med1">Hello World!</span>
	 * @param html
	 */
	public static TransscriptElement extractTransscriptElement(String html) {
		String lId;
		try {
			lId=html.substring(
				html.indexOf("id=\"") + 4, 
				html.indexOf("\""));
		} catch (Exception e) {
			lId = "";
		}
		
		String levelSt;
		try {
			levelSt=	html.substring(
							html.indexOf("class=\"gc-word-") + 15, 
							html.indexOf("\""));
		} catch (Exception e) {
			levelSt = "";
		}
		
		String ltext;
		try {
			ltext=html.substring(
				  html.indexOf(">") + 1, 
				  html.indexOf("</span>"));
		} catch (Exception e) {
			ltext = "";
		}
		
		if(levelSt.equals("med1")) {
			return new TransscriptElement(ltext, lId, RecognitionLevel.MED1);
		} else if(levelSt.equals("med2")) {
			return new TransscriptElement(ltext, lId, RecognitionLevel.MED2);
		} else if(levelSt.equals("high")) {
			return new TransscriptElement(ltext, lId, RecognitionLevel.HIGH);
		} else {
			return new TransscriptElement(ltext, lId, RecognitionLevel.UNKNOWN);
		}

	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the level
	 */
	public RecognitionLevel getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(RecognitionLevel level) {
		this.level = level;
	}
	
	
}
