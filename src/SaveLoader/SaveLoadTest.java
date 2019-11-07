package SaveLoader;

import Event.Answer;
import Event.SimpleEvent;
import Game.TestStorage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

public class SaveLoadTest {

    @Test
    public void saveLoadGame() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        messages.add("/save");
        messages.add("test1");
        messages.add("/exit");
        AbstractSaveLoader loader = new AbstractSaveLoader();
        TestStorage storage = new TestStorage();

        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        storage.addEvent("2", new SimpleEvent("2", "2", "2",
                new Answer[]{new Answer("1", "-1", new HashMap<>())}, false, false));
        Game.IOInterface console = new Game.Test1IO(messages);
        Game.Game game = new Game.Game(console, storage, loader);
        game.startGameAtID("1");

        assertTrue("error game not saved", loader.isGameSaved);
        assertEquals("error wrong filename", loader.savedFilename, "test1");
        assertTrue("error wrong stack", loader.savedGameInfo.getIDstack().empty());
        assertEquals("error wrong event to start", loader.savedGameInfo.getEventToStart(), "2");
        assertTrue("error wrong player info", loader.savedGameInfo.getPlayerData().isEmpty());
    }
}
