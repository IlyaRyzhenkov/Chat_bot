package SaveLoader;

import Event.Answer;
import Event.SimpleEvent;
import EventStorage.EventStorage;
import EventStorage.ILoader;
import Game.TestStorage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

public class SaveLoadTest {

    @Test
    public void SaveGame()
    {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("load");
        messages.add("filename");
        messages.add("save");
        messages.add("test1");
        AbstractSaveLoader loader = new AbstractSaveLoader();
        TestStorage storage = new TestStorage();

        storage.addEvent("1", new SimpleEvent("1", "1", "1",
                new Answer[]{new Answer("1", "2", new HashMap<>())}, false, false));
        Game.IOInterface console = new Game.Test1IO(messages);
        Game.Game game = new Game.Game(console, storage, loader);
        game.startGameAtID("1");
        assertEquals("error wrong filename", loader.savedFilename, "test1");
        assertFalse("error wrong stack", loader.savedGameInfo.getIDstack().empty());
        assertEquals("error wrong event to start", loader.savedGameInfo.getEventToStart(), "1");
        assertFalse("error wrong player info", loader.savedGameInfo.getPlayerData().isEmpty());
    }
}
