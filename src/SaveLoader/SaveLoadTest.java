package SaveLoader;

import Checker.EldritchHorrorChecker;
import Checker.IChecker;
import Event.Answer;
import Event.SimpleEvent.SimpleEvent;
import Game.Message;
import Game.TestEventStorage;
import Player.Player;
import Storage.IItemStorage;
import Storage.ItemStorage;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class SaveLoadTest {

    @Test
    public void saveGame() {
        AbstractSaveLoader loader = new AbstractSaveLoader();
        TestEventStorage storage = new TestEventStorage();
        IChecker checker = new EldritchHorrorChecker();
        IItemStorage itemStorage = new ItemStorage();

        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        Game.OInterface console = new Game.Test1IO(null);
        Game.Game game = new Game.Game(console, storage, loader, checker, itemStorage);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "/save test1"));
        game.makeEventIteration(new Message("player", "/exit"));

        assertTrue("error game not saved", loader.isGameSaved);
        assertEquals("error wrong filename", loader.savedFilename, "test1");
        assertTrue("error wrong stack", loader.savedGameInfo.getIDstack().empty());
        assertEquals("error wrong event to start", loader.savedGameInfo.getEventToStart(), "2");
        assertTrue("error wrong player info", loader.savedGameInfo.getPlayerData().isEmpty());
    }

    @Test
    public void loadGame() {
        AbstractSaveLoader loader = new AbstractSaveLoader();
        IChecker checker = new EldritchHorrorChecker();
        IItemStorage itemStorage = new ItemStorage();
        TestEventStorage storage = new TestEventStorage();
        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        Game.OInterface console = new Game.Test1IO(null);
        Game.Game game = new Game.Game(console, storage, loader, checker, itemStorage);
        game.setInitialID("1");
        game.makeEventIteration(new Message("player", "1"));
        game.makeEventIteration(new Message("player", "/load 123"));

        Player player = game.getPlayerTable().get("player");
        assertEquals("wrong player id", "player", player.getId());
        assertEquals("wrong player data", "2", player.getImportantData().get("1"));
        assertEquals("wrong player stack", new String[] {"123"}, player.getEventStack().toArray());
        assertEquals("wrong event to start", "1", player.getCurrentEvent());
    }
}
