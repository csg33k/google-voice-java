package test.datatypes;

import com.techventus.server.voice.datatypes.ActiveForwardingId;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Brendon Shih
 *
 */
// Coverage holes
//	ActiveForwardingId.createActiveForwardingIdListFromJsonPartResponse line 34 not sure how to test this, do not know jsonPart format.
//	ActiveForwardingId.createActiveForwardingIdListFromJsonPartResponse line 37 not sure how to test this, do not know jsonPart format.
public class ActiveForwardingIdTest {

    List<ActiveForwardingId> blankArrayList = new ArrayList<ActiveForwardingId>();
    
    @Test
    public void testEmptyString() {
        Assert.assertEquals(blankArrayList, ActiveForwardingId.createActiveForwardingIdListFromJsonPartResponse(""));
    }

}
