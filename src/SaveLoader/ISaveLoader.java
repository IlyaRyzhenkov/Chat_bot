package SaveLoader;

public interface ISaveLoader {
    void saveGame(String filename, GameInfo gameData);

    GameInfo loadGame(String filename);
}
