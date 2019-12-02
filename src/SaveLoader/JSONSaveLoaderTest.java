package SaveLoader;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import static org.junit.Assert.*;

public class JSONSaveLoaderTest {

    @Test
    public void testLoadCorrectFile() {
        JSONSaveLoader loader = new JSONSaveLoader();
        GameInfo info = loader.loadGame("events/test/correct_savefile.json");
        assertEquals("Wrong event to start id","Start", info.getEventToStart());

        Stack<String> stack = info.getIdStack();
        String[] expectedStack = {"first", "second"};
        assertEquals("Wrong stack size", 2, stack.size());
        for (int i = 0; i < expectedStack.length; i++) {
            assertEquals("Wrong " + i + " element of stack", expectedStack[i], stack.get(i));
        }

        HashMap<String, String> playerInfo = info.getPlayerData();
        String[] keys = {"key1", "key2"};
        String[] values = {"val1", "val2"};
        assertEquals("Wrong player info size", 2, playerInfo.size());
        for (int i = 0; i < keys.length; i++) {
            assertEquals("Wrong value on key:" + keys[i], values[i], playerInfo.get(keys[i]));
        }
    }

    @Test
    public void testLoadDamagedFile() {
        JSONSaveLoader loader = new JSONSaveLoader();
        GameInfo info = loader.loadGame("events/test/damaged_savefile.json");
        assertNull("Game info not null", info);
    }

    @Test
    public void testLoadWithWrongFullFilename() {
        JSONSaveLoader loader = new JSONSaveLoader();
        GameInfo info = loader.loadGame("bad_filename.json");
        assertNull("Game info not null", info);
    }

    @Test
    public void testLoadWithWrongFilename() {
        JSONSaveLoader loader = new JSONSaveLoader();
        GameInfo info = loader.loadGame("bad_filename");
        assertNull("Game info not null", info);
    }

    @Test
    public void testSaveFile() {
        String filename = "events/test/test_savedgame.json";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        Stack<String> stack = new Stack<>();
        stack.add("first");
        stack.add("second");
        HashMap<String, String> pInfo = new HashMap<>();
        pInfo.put("key1", "val1");
        pInfo.put("key2", "val2");

        GameInfo info = new GameInfo(stack, pInfo, "Start", 5, 5, new ArrayList<>(), "", "", "", new HashMap<String, Integer>() {
            {
                put("knowledge", 5);
                put("strength", 5);
                put("communication", 5);
                put("attention", 5);
                put("luck", 5);
            }
        });
        JSONSaveLoader saver = new JSONSaveLoader();
        saver.saveGame(filename, info);

        String actual = "";
        try(FileReader reader = new FileReader(filename)) {
            int c;
            while((c = reader.read()) != -1) {
                actual += (char)c;
            }
        }
        catch (IOException e) {
            assertNull(e);
        }

        String expected ="{\"maxHp\":5,\"stack\":[\"first\",\"second\"],\"Player data\":[[\"key1\",\"val1\"],[\"key2\",\"val2\"]],\"StartEvent\":\"Start\",\"hp\":5,\"attributes\":[{\"attribute\":\"luck\",\"value\":5},{\"attribute\":\"strength\",\"value\":5},{\"attribute\":\"attention\",\"value\":5},{\"attribute\":\"communication\",\"value\":5},{\"attribute\":\"knowledge\",\"value\":5}],\"inventory\":{\"weapon\":\"\",\"suit\":\"\",\"accessory\":\"\",\"items\":[]}}";
        assertEquals("Files not same", expected, actual);
        File file2 = new File(filename);
        if (file2.exists()) {
            file2.delete();
        }
    }
}
