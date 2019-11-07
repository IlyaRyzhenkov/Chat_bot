package Event;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SimpleEventTest {
    @Test
    public void testReply() {
        SimpleEvent event = new SimpleEvent("1", "1", "1", new Answer[] {
                new Answer("1", "2", new HashMap<>()),
                new Answer("2", "3", new HashMap<>()),
                new Answer("3", "4", new HashMap<>()) }, false, false);
        String[] actual = new String[5];
        actual[0] = event.reply("1");
        actual[1] = event.reply("2");
        actual[2] = event.reply("3");
        actual[3] = event.reply("4");
        actual[4] = event.reply("abc");
        String incorrect_reply_id = "Exceptions/incorrect_reply";
        String[] expected = {"2", "3", "4", incorrect_reply_id, incorrect_reply_id};

        for(int i = 0; i < 5; i++){
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testToString() {
        SimpleEvent event = new SimpleEvent("1", "name", "text", new Answer[] {
                new Answer("ans1", "2", new HashMap<>()),
                new Answer("ans2", "3", new HashMap<>()),
                new Answer("ans3", "4", new HashMap<>()) }, false, false);
        String actual = event.toString();
        String expected = "text\n1. ans1\n2. ans2\n3. ans3\n";
        assertEquals(expected, actual);
    }
}