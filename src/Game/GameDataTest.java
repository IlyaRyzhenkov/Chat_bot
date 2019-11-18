package Game;

import Checker.EldritchHorrorChecker;
import Checker.iChecker;
import Event.Answer;
import Event.SimpleEvent.SimpleEvent;
import SaveLoader.AbstractSaveLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameDataTest {

    @Test
    public void addParentToStack() {
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>())}, false, true));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader, checker);

        game.setInitialID("test1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));

        assertEquals(1, game.getPlayerTable().get("player").getEventStack().size());
        assertEquals("test1", game.getPlayerTable().get("player").getEventStack().get(0));
    }


    @Test
    public void getParentFromStack() {
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>()),
                        new Answer("2", "test3", new HashMap<>())}, false, true));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "", new HashMap<>())}, false, false));
        storage.addEvent("test3", new SimpleEvent("test3", "test3", "test3",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("test1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "2"));
        game.makeEventIteration(new Message("player", "1"));

        assertEquals(1, game.getPlayerTable().get("player").getEventStack().size());
    }

    @Test
    public void rememberData() {
        AbstractSaveLoader loader = new AbstractSaveLoader();
        Test1IO console = new Test1IO(null);
        iChecker checker = new EldritchHorrorChecker();

        TestStorage storage = new TestStorage();
        storage.addEvent("test1", new SimpleEvent("test1", "test1", "test1",
                new Answer[]{new Answer("1", "test2", new HashMap<>())}, true, false));
        storage.addEvent("test2", new SimpleEvent("test2", "test2", "test2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));

        Game game = new Game(console, storage, loader, checker);
        game.setInitialID("test1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));

        assertEquals(1, game.getPlayerTable().get("player").getImportantData().size());
        assertTrue(game.getPlayerTable().get("player").getImportantData().containsKey("test1"));
        assertEquals("1", game.getPlayerTable().get("player").recall("test1"));
    }
}