package com.techventus.server.voice.util;

/**
 * Collection of useful html parsing methods
 * 
 * @author Tobias Eisentraeger
 *
 */
public abstract class ParsingUtil {

	/**
	 * Strips the text from the uninteresting parts before and after the interesting part.
	 * The return includes borders if includeBorders == true - Returns null when Exception occures<br/><br/>
	 * Example:<br/>
	 * removeUninterestingParts("Hello Toby  , How are you today? Fine.", "How are", "?" , <b>true</b>)<br/>
	 * Returns: "How are you today?"<br/>
	 * <br/>
	 * removeUninterestingParts("Hello Joseph, How are you today? Fine.", "How are", "?" , <b>false</b>)<br/>
	 * Returns: " you today"
	 * 
	 * @param text
	 * @param startBorder
	 * @param endBorder
	 * @param includeBorders
	 * @return
	 */
	public static final String removeUninterestingParts(String text, String startBorder, String endBorder, boolean includeBorders) {
		String ret;
		if(text!=null&&startBorder!=null&&endBorder!=null&&(text.indexOf(startBorder)!=-1)&&(text.indexOf(endBorder)!=-1) ) {
			try {
				if(includeBorders) {
					text = text.substring(text.indexOf(startBorder));
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder)+endBorder.length());
					} else {
						ret = null;
					}
				} else {
					text = text.substring(text.indexOf(startBorder)+startBorder.length());
					if(text!=null) {
						ret = text.substring(0,text.indexOf(endBorder));
					} else {
						ret = null;
					}
				}
			} catch (Exception e) {
				System.out.println("Exception "+e.getMessage());
				System.out.println("Begin:"+startBorder);
				System.out.println("End:"+endBorder);
				System.out.println("Text:"+text);
				e.printStackTrace();
				ret = null;
			}
		} else {
			ret = null;
		}
		return ret;
	}
	
}
