package test.util;

import junit.framework.Assert;

import org.junit.Test;

import com.techventus.server.voice.util.ParsingUtil;
/**
 * 
 * @author bFutral
 *
 */
public class ParsingUtilTest {
	
	ParsingUtil parsingUtil;
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUninterestingPartsNullEndBorder() {
		
		Assert.assertEquals(null, parsingUtil.removeUninterestingParts("Hello Toby, How are you today? Fine.", "How are", null, true));
	}

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUninterestingPartsNullStartBorder() {
		
		Assert.assertEquals(null, parsingUtil.removeUninterestingParts("Hello Toby, How are you today? Fine.", null, "?", true));	
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUninterestingPartsNullText() {
		
		Assert.assertEquals(null, parsingUtil.removeUninterestingParts(null, "How are",  "?", true));
	}
		
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUninterestingPartsIncludeBordersTrue() {
		
		Assert.assertEquals("How are you today?", parsingUtil.removeUninterestingParts("Hello Toby, How are you today? Fine.", "How are", "?" , true));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUninterestingPartsIncludeBordersFalse() {
		
		Assert.assertEquals(" you today", parsingUtil.removeUninterestingParts("Hello Toby, How are you today? Fine.", "How are", "?" , false));
	}

}
