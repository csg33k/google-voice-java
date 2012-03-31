package test.util;

import com.techventus.server.voice.util.SMSParser;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SMSParserTest {
    private SMSParser testSMSParser;

    @Before
    public void setup() {
        testSMSParser = new SMSParser("test", "5030000000");
    }

    @Test
    public void testParsePhoneNumber() {
        //testSMSParser.parsePhoneNumber("5035551212");
        Assert.assertEquals("+15035551212", testSMSParser.parsePhoneNumber("5035551212"));
    }


}
