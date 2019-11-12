package SaveLoader;

import Player.Player;

import java.util.HashMap;
import java.util.Stack;

public class GameInfo {
    private Stack<String> IDstack;
    private HashMap<String, String> playerData;
    private String eventToStart;

    public GameInfo(Stack<String> idstack, HashMap<String, String> playerData,
                    String eventIdToStart) {
        IDstack = idstack;
        this.playerData = playerData;
        eventToStart = eventIdToStart;
    }

    public GameInfo(Player player) {
        IDstack = player.getEventStack();
        playerData = player.getImportantData();
        eventToStart = player.getCurrentEvent();
    }

    public Stack<String> getIDstack(){
        return IDstack;
    }

    public HashMap<String, String> getPlayerData() {
        return playerData;
    }

    public String getEventToStart() {
        return eventToStart;
    }
}
