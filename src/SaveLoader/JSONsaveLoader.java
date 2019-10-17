package SaveLoader;
import Event.Event;
import Event.Answer;
import Player.Player;
import org.json.simple.JSONObject;

import java.util.Stack;

public class JSONsaveLoader implements ISaveLoader {

    public void saveGame(String filename, GameInfo gameData) {
        filename += ".json";
        JSONObject obj = new JSONObject();
        obj.put("StartEvent", gameData.getEventToStart());
    }

    public GameInfo loadGame(String filename) {
        return null;
    }
}
