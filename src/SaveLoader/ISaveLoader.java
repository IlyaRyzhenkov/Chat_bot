package SaveLoader;

import Player.Player;

import java.util.Stack;

public interface ISaveLoader {
    public void saveGame(String filename, GameInfo gameData);

    public GameInfo loadGame(String filename);
}
