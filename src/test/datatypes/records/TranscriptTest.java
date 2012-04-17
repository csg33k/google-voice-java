package test.datatypes.records;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.techventus.server.voice.datatypes.records.Transcript;
import com.techventus.server.voice.datatypes.records.TranscriptElement;

/**
 * @author bFutral
 * 
 */
// Coverage holes
// Transcript.toString ln 50 unreachable, null elements cause NPE;
// Transcript.toString ln 52 unreachable, null elements cause NPE;
public class TranscriptTest {
	// testTranscripts
	Transcript testTranscript;
	Transcript testTranscript1;
	// Params for testTranscripts
	final TranscriptElement.RecognitionLevel testLevel = TranscriptElement.RecognitionLevel.HIGH;
	final TranscriptElement.RecognitionLevel testLevel1 = TranscriptElement.RecognitionLevel.MED1;
	final TranscriptElement testElement = new TranscriptElement("testText",
			"testID", testLevel);
	final TranscriptElement testElement1 = new TranscriptElement(null, null,
			null);

	@Before
	public void setUp() {
		// Param for testTrascript
		final List<TranscriptElement> testList = new ArrayList<TranscriptElement>();
		testList.add(testElement);
		// Instantiate testTranscript
		testTranscript = new Transcript(testList);

		// Param testTranscript1
		final List<TranscriptElement> testList1 = new ArrayList<TranscriptElement>();
		testList1.add(testElement);
		testList1.add(testElement1);
		// Instantiate testTranscript1
		testTranscript1 = new Transcript(testList1);
	}

	@Test
	public void testToStringOverrideOneElement() {

		Assert.assertEquals("testText", testTranscript.toString());
	}

	@Test
	public void testToStringOverrideTwoElements() {

		Assert.assertEquals("testText null", testTranscript1.toString());

	}

}
