package Game;

import Event.Answer;
import Event.SimpleEvent;
import SaveLoader.AbstractSaveLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameDataTest {

    @Test
    public void addParentToStack() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("1");
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>())}, false, true));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader);
        game.startGameAtID("test1");
        assertEquals(1, game.getParentIDs().size());
        assertEquals("test1", game.getParentIDs().get(0));
    }


    @Test
    public void getParentFromStack() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("1");
        messages.add("2");
        messages.add("1");
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>()),
                        new Answer("2", "3", new HashMap<>())}, false, true));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "", new HashMap<>())}, false, false));
        storage.addEvent("test3", new SimpleEvent("test3", "test3", "test3",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));


        Game game = new Game(console, storage, loader);
        game.startGameAtID("test1");
        assertEquals(0, game.getParentIDs().size());
    }

    @Test
    public void rememberData() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("1");
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(messages);

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>())}, true, false));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader);
        game.startGameAtID("test1");
        assertEquals(1, game.getPlayer().getImportantData().size());
        assertTrue(game.getPlayer().getImportantData().containsKey("test1"));
        assertEquals("1", game.getPlayer().recall("test1"));
    }
}