package Game;

import Checker.EldritchHorrorChecker;
import Checker.iChecker;
import Event.ExceptionEvent.ExceptionEvent;
import Event.SimpleEvent.SimpleEvent;
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
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        storage.addEvent("Exceptions/incorrect_reply", new ExceptionEvent("Exceptions/incorrect_reply", "exp", "exp",
                new Answer[]{new Answer("1", "1", new HashMap<>())}));

        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "asd"));
        game.makeEventIteration(new Message("player", "asd"));

        String[] expected = {"1\n1. 1\n", "2\n1. 1\n", incorrect_reply_message, "2\n1. 1\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testIncorrectReplies2() {
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        storage.addEvent("Exceptions/incorrect_reply", new ExceptionEvent("Exceptions/incorrect_reply", "exp", "exp",
                new Answer[]{new Answer("1", "1", new HashMap<>())}));

        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "3"));
        game.makeEventIteration(new Message("player", "asd"));

        String[] expected = {"1\n1. 1\n", "2\n1. 1\n", incorrect_reply_message, "2\n1. 1\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testEventAnswersText(){
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "name", "event_text", new Answer[]{
                new Answer("answer1_text", "1", new HashMap<>()),
                new Answer("answer2_text", "-1", new HashMap<>()),
                new Answer("answer3_text", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));

        String[] expected = {
                "event_text\n1. answer1_text\n2. answer2_text\n3. answer3_text\n",
                "event_text\n1. answer1_text\n2. answer2_text\n3. answer3_text\n"};
        assertEquals(console.received_replies.size(), expected.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(console.received_replies.get(i), expected[i]);
        }
    }

    @Test
    public void testHelpCommand() {
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "/help"));

        String expectedHelpMessage = "Story-game bot\n" +
                "You can win or lose by answering bots questions.\n" +
                "You can save the current state of game by '/save <<filename>>' command.\n" +
                "You can load the game state by '/load <<filename>>' command.\n" +
                "You can stop the game by '/exit' command.\n" +
                "You should'nt answering the questions by this commands and the 'default' word.\n" +
                "Good luck and have fun!";

        assertEquals("wrong answers count", 3, console.received_replies.size());
        assertEquals("wrong first message", "1\n1. 1\n", console.received_replies.get(0));
        assertEquals("wrong help message", expectedHelpMessage, console.received_replies.get(1));
        assertEquals("wronf third message", "1\n1. 1\n", console.received_replies.get(2));
    }

    @Test
    public void testExitCommand() {
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "/exit"));

        assertFalse("game not stoped", game.getPlayerTable().containsKey("player"));
        assertEquals("wrong message count", 2, console.received_replies.size());
        assertEquals("wrong first message", "1\n1. 1\n", console.received_replies.get(0));
        assertEquals("wrong exit message", "exit from game", console.received_replies.get(1));
    }
}