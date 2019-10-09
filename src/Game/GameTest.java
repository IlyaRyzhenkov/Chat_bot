package Game;

import Event.Event;
import Event.Answer;
import EventStorage.ILoader;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private String incorrect_reply_message = "incorrect reply";

    @Test
    public void testIncorrectReplies() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("2");
        messages.add("abc");
        messages.add("1");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new Event("1", "1", "1", new Answer[]{new Answer("1", "2")}));
        storage.addEvent("2", new Event("2", "2", "2", new Answer[]{new Answer("1", "-1")}));

        Game game = new Game(console, storage);
        game.startGameAtID("1");

        String[] expected = {"1", "1. 1\n", "2", "1. 1\n", incorrect_reply_message, "1. 1\n", incorrect_reply_message, "1. 1\n"};

        assertEquals(console.recived_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.recived_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testEventAnswersText(){
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new Event("1", "name", "event_text", new Answer[]{
                new Answer("answer1_text", "-1"),
                new Answer("answer2_text", "-1"),
                new Answer("answer3_text", "-1")}));

        Game game = new Game(console, storage);
        game.startGameAtID("1");

        String[] expected = {"event_text", "1. answer1_text\n2. answer2_text\n3. answer3_text\n"};
        assertEquals(console.recived_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.recived_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testStartWithWrongId(){
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        Game game = new Game(console, storage);
        game.startGameAtID("1");

        assertEquals(console.recived_replies.size(), 0);
    }
}