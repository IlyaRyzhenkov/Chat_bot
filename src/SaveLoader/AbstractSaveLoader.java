package SaveLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AbstractSaveLoader implements ISaveLoader {
    private String savedFilename;
    private GameInfo savedGameInfo;
    private boolean isGameSaved = false;

    public String getSavedFilename() { return savedFilename; }

    public GameInfo getSavedGameInfo() { return savedGameInfo; }

    public boolean isGameSaved() { return isGameSaved; }

    public void saveGame(String filename, GameInfo gameData) {
        savedFilename = filename;
        savedGameInfo = gameData;
        isGameSaved = true;
    }

    public GameInfo loadGame(String filename) {
        Stack<String> idStack = new Stack<String>();
        idStack.add("123");
        HashMap<String, String> playerInfo = new HashMap<String, String>();
        playerInfo.put("1", "2");
        return new GameInfo(idStack, playerInfo, "1", 5, 5, new ArrayList<>(), "", "", "", new HashMap<String, Integer>() {
            {
                put("knowledge", 5);
                put("strength", 5);
                put("communication", 5);
                put("attention", 5);
                put("luck", 5);
            }
        });
    }
}
