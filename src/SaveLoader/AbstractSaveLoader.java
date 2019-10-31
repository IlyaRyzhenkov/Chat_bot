package SaveLoader;

import Game.Game;
import Player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AbstractSaveLoader implements ISaveLoader {
    public String savedFilename;
    public GameInfo savedGameInfo;
    public boolean isGameSaved = false;

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
        return new GameInfo(idStack, playerInfo, "1");
    }
}
