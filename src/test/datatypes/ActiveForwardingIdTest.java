package test.datatypes;

import com.techventus.server.voice.datatypes.ActiveForwardingId;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ActiveForwardingIdTest {

    List<ActiveForwardingId> blankArrayList = new ArrayList<ActiveForwardingId>();


    @Test
    public void testEmptyString() {
        Assert.assertEquals(blankArrayList, ActiveForwardingId.createActiveForwardingIdListFromJsonPartResponse(""));
    }

}
