package Event.AnyAnswerEvent;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AnyAnswerEventTest {

    private AnyAnswerEvent event;

    public AnyAnswerEventTest() {
        event = new AnyAnswerEvent("testID", "testName", "testText",
                new Answer[]{new Answer("default", "testID_default", new HashMap<String, String>()),
                new Answer("not_default", "testID_not_default", new HashMap<String, String>())}, false, false);
    }

    @Test
    public void replyDefaultTest() {
        String actual = event.reply("foo");
        assertEquals("testID_default", actual);
    }


    @Test
    public void replySpecialTest() {
        String actual = event.reply("not_default");
        assertEquals("testID_not_default", actual);
    }

    @Test
    public void testToString() {
        String actual = event.toString();
        assertEquals("testText", actual);
    }
}