package test.datatypes.records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.Contact;
import com.techventus.server.voice.datatypes.records.Call;
import com.techventus.server.voice.datatypes.records.Record;
import com.techventus.server.voice.datatypes.records.ShortMessage;
import com.techventus.server.voice.datatypes.records.Transcript;
import com.techventus.server.voice.datatypes.records.TranscriptElement;
import com.techventus.server.voice.datatypes.records.Voicemail;

/**
 * 
 * @author Brett Futral @ Catalyst IT Services
 *
 */
public class RecordTest {
	//testRecords
	Record testRecord;
	Record testRecord1;
	Record testRecord2;
	
	//Record Constructors

	
	//params for testRecords
	final Contact CONTACT = new Contact("testName", "testID", "testNumber",
			"testURL");
	final Date DATE = new Date(1321038671000l);
	final static TranscriptElement.RecognitionLevel testLevel = TranscriptElement.RecognitionLevel.HIGH;
	final TranscriptElement testElement = new TranscriptElement("testText",
			"testID", testLevel);
	
	@Before
	public void setUp() {
		
		final List<TranscriptElement> testList = new ArrayList<TranscriptElement>();
		testList.add(testElement);
		
		final Transcript testTranscript = new Transcript(testList);
		
		testRecord = new Voicemail("ID1", "testTitle", DATE, CONTACT, testTranscript, true);
		testRecord1 = new Call("ID1", "testTitle", DATE, CONTACT, true);
		testRecord2 = new ShortMessage("ID1", "testTitle", DATE, CONTACT, true);
	}

	@Test
	public void testIsVoicMailFalseCall() {

		final boolean voicemailTestFalseCall = testRecord1.isVoicemail();

		Assert.assertEquals(false, voicemailTestFalseCall);
	}

	@Test
	public void testIsVoicMailFalseShortMessage() {

		final boolean voicemailTestFalseShortMessage = testRecord2.isVoicemail();

		Assert.assertEquals(false, voicemailTestFalseShortMessage);
	}
	
	@Test
	public void testIsVoicMailTrue() {

		final boolean voicemailTesttrue = testRecord.isVoicemail();

		Assert.assertEquals(true, voicemailTesttrue);
	}

	@Test
	public void testIsCallFalseVoicemail() {

		final boolean callTestFalseVoicemail = testRecord.isCall();

		Assert.assertEquals(false, callTestFalseVoicemail);
	}
	
	@Test
	public void testIsCallFalseShortMessage() {

		final boolean callTestFalseShortMessage = testRecord2.isCall();

		Assert.assertEquals(false, callTestFalseShortMessage);
	}

	@Test
	public void testIsCallTrue() {

		final boolean callTestTrue = testRecord1.isCall();

		Assert.assertEquals(true, callTestTrue);
	}

	@Test
	public void testIsShortMessageFalseVoicemail() {

		final boolean shortMessageTestFalseCall = testRecord.isShortMessage();

		Assert.assertEquals(false, shortMessageTestFalseCall);
	}
	
	@Test
	public void testIsShortMessageFalseCall() {

		final boolean shortMessageTestFalseCall = testRecord1.isShortMessage();

		Assert.assertEquals(false, shortMessageTestFalseCall);
	}

	@Test
	public void testIsShortMessageTrue() {

		final boolean shortMessageTestTrue = testRecord2.isShortMessage();

		Assert.assertEquals(true, shortMessageTestTrue);
	}

}
