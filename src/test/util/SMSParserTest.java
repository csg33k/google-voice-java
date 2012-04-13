package test.util;

import com.techventus.server.voice.datatypes.records.SMSThread;
import com.techventus.server.voice.util.SMSParser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bFutral
 * 
 */
// Holes in coverage:
// SMSParser.parsePhoneNumber line 258 will always == -1 due to line
// regex filter in line 257)
// Cannot test getSMSThreads due to private method call inside method.
// Cannot test getSMSThreads due to private method call inside method.
@SuppressWarnings("rawtypes")
public class SMSParserTest {

	// TestSMSParser
	private SMSParser testSMSParser;

	// Reflection Setup buildSMSThreadMap
	private Method bSMSTM;
	private static final String TEST_BSMSTM = "buildSMSThreadMap";
	private Class[] bSMSTMParamTypes;
	private Object[] bSMSTMParams;

	@Before
	public void setUp() throws NoSuchMethodException, SecurityException {
		// Instantiate testSMSParser
		testSMSParser = new SMSParser("test", "5030000000");

		// Reflect class/method buildSMSThreadMap
		bSMSTMParamTypes = new Class[1];
		bSMSTMParamTypes[0] = java.lang.String.class;
		bSMSTM = testSMSParser.getClass().getDeclaredMethod(TEST_BSMSTM,
				bSMSTMParamTypes);
		bSMSTM.setAccessible(true);
		bSMSTMParams = new Object[1];
	}

	@Test
	public void testParsePhoneNumber() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// Reflection Setup parsePhoneNumber
		Method pPN;
		String testPPN = "parsePhoneNumber";
		Class[] pPNParamTypes;
		Object[] pPNParams;

		// Reflect class/method parsePhoneNumber
		pPNParamTypes = new Class[1];
		pPNParamTypes[0] = java.lang.String.class;
		pPN = testSMSParser.getClass()
				.getDeclaredMethod(testPPN, pPNParamTypes);
		pPN.setAccessible(true);
		pPNParams = new Object[1];
		pPNParams[0] = "5035551212";

		final String testPPNumber = (String) pPN.invoke(testSMSParser,
				pPNParams);

		Assert.assertEquals("+15035551212", testPPNumber);

	}

	@Test
	public void testBuildSMSThreadMap() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		long dateLong = 1334348667447L;
		final Date validDate = new Date(dateLong);

		final SMSThread testSMSThread = new SMSThread("testID", "test note",
				validDate, null, true, true);

		String testSMSResponse = "<json><![CDATA[{\"messages\":{\"testID\":{\"id\":\"testID\",\"phoneNumber\":\"+15035551212\",\"displayNumber\":\"(503) 555-1212\",\"startTime\":\"1334348667447\",\"displayStartDateTime\":\"4/13/12 1:24 PM\",\"displayStartTime\":\"1:24 PM\",\"relativeStartTime\":\"3 minutes ago\",\"note\":\"test note\",\"isRead\":true,\"isSpam\":false,\"isTrash\":false,\"star\":true,\"messageText\":\"testSMS\",\"labels\":[\"sms\",\"all\"],\"type\":11,\"children\":\"\"}},\"totalSize\":1,\"unreadCounts\":{\"all\":0,\"inbox\":0,\"sms\":0,\"unread\":0,\"voicemail\":0},\"resultsPerPage\":10}]]></json>";

		final Map<String, SMSThread> testMap = new HashMap<String, SMSThread>();
		testMap.put("testID", testSMSThread);

		bSMSTMParams[0] = testSMSResponse;

		final Map testBSMSTMap = (Map) bSMSTM.invoke(testSMSParser,
				bSMSTMParams);

		Assert.assertEquals(testMap.toString(), testBSMSTMap.toString());

	}

	@Test
	public void testBuildSMSThreadMapNoInputs() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		final Date epochDate = new Date(0);

		final SMSThread testSMSThread1 = new SMSThread("", "", epochDate, null,
				false, false);

		String testSMSResponse1 = "<json><![CDATA[{\"messages\":{\"testID\":{\"phoneNumber\":\"+15035551212\",\"displayNumber\":\"(503) 555-1212\",\"displayStartDateTime\":\"4/13/12 1:24 PM\",\"displayStartTime\":\"1:24 PM\",\"relativeStartTime\":\"3 minutes ago\",\"isSpam\":false,\"isTrash\":false,\"messageText\":\"testSMS\",\"labels\":[\"sms\",\"all\"],\"type\":11,\"children\":\"\"}},\"totalSize\":1,\"unreadCounts\":{\"all\":0,\"inbox\":0,\"sms\":0,\"unread\":0,\"voicemail\":0},\"resultsPerPage\":10}]]></json>";

		final Map<String, SMSThread> testMap1 = new HashMap<String, SMSThread>();
		testMap1.put("", testSMSThread1);

		bSMSTMParams[0] = testSMSResponse1;

		final Map testBSMSTMNoInputs = (Map) bSMSTM.invoke(testSMSParser,
				bSMSTMParams);

		Assert.assertEquals(testMap1.toString(), testBSMSTMNoInputs.toString());

	}

}
