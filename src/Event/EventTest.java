package Event;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {
    private String incorrect_reply_id = "Incorrect";
    @Test
    public void testReply() {
        Event event = new Event("1", "1", "1", new Answer[]{
                new Answer("1", "2"),
                new Answer("2", "3"),
                new Answer("3", "4")});
        String[] actual = new String[5];
        actual[0] = event.reply("1");
        actual[1] = event.reply("2");
        actual[2] = event.reply("3");
        actual[3] = event.reply("4");
        actual[4] = event.reply("abc");
        String[] expected = {"2", "3", "4", incorrect_reply_id, incorrect_reply_id};

        for(int i = 0; i < 5; i++){
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void getText() {
        Event event = new Event("1", "name", "text", new Answer[]{
                new Answer("ans1", "2"),
                new Answer("ans2", "3"),
                new Answer("ans3", "4")});
        String actual = event.getText();
        String expected = "text";
        assertEquals(expected, actual);
    }

    @Test
    public void getAnswers() {
        Event event = new Event("1", "name", "text", new Answer[]{
                new Answer("ans1", "2"),
                new Answer("ans2", "3"),
                new Answer("ans3", "4")});
        String actual = event.getAnswers();
        String expected = "1. ans1\n2. ans2\n3. ans3\n";
        assertEquals(expected, actual);
    }
}