package Game;

import Event.ExceptionEvent;
import Event.SimpleEvent;
import Event.Answer;
import SaveLoader.AbstractSaveLoader;
import SaveLoader.ISaveLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTest {
    private String incorrect_reply_message = "exp\n1. 1\n";
    private ISaveLoader loader = new AbstractSaveLoader();

    @Test
    public void testIncorrectReplies1() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("asd");
        messages.add("asd");
        messages.add("/exit");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        storage.addEvent("Exceptions/incorrect_reply", new ExceptionEvent("Exceptions/incorrect_reply", "exp", "exp",
                new Answer[]{new Answer("1", "1", new HashMap<>())}));

        Game game = new Game(console, storage, loader);
        game.startGameAtID("1");

        String[] expected = {"1\n1. 1\n", "2\n1. 1\n", incorrect_reply_message, "2\n1. 1\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testIncorrectReplies2() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("3");
        messages.add("asd");
        messages.add("/exit");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        storage.addEvent("Exceptions/incorrect_reply", new ExceptionEvent("Exceptions/incorrect_reply", "exp", "exp",
                new Answer[]{new Answer("1", "1", new HashMap<>())}));

        Game game = new Game(console, storage, loader);
        game.startGameAtID("1");

        String[] expected = {"1\n1. 1\n", "2\n1. 1\n", incorrect_reply_message, "2\n1. 1\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testEventAnswersText(){
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("/exit");
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "name", "event_text", new Answer[]{
                new Answer("answer1_text", "1", new HashMap<>()),
                new Answer("answer2_text", "-1", new HashMap<>()),
                new Answer("answer3_text", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader);
        game.startGameAtID("1");

        String[] expected = {
                "event_text\n1. answer1_text\n2. answer2_text\n3. answer3_text\n",
                "event_text\n1. answer1_text\n2. answer2_text\n3. answer3_text\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }
}