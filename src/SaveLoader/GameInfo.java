package SaveLoader;

import Item.Item;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameInfo {
    private Stack<String> idStack;
    private HashMap<String, String> playerData;
    private HashMap<String, Integer> playerAttributes;
    private String eventToStart;
    private ArrayList<String> items;
    private String suitId;
    private String weaponId;
    private String accessoryId;
    private int playerHp;
    private int maxPlayerHp;

    public GameInfo(Stack<String> idStack, HashMap<String, String> playerData, String eventIdToStart,
                    int playerHp, int maxPlayerHp, ArrayList<String> items, String weaponId, String suitId, String accessoryId,
                    HashMap<String, Integer> playerAttributes) {
        this.idStack = idStack;
        this.playerData = playerData;
        this.eventToStart = eventIdToStart;
        this.playerAttributes = playerAttributes;
        this.items = items;
        this.weaponId = weaponId;
        this.accessoryId = accessoryId;
        this.suitId = suitId;
        this.playerHp = playerHp;
        this.maxPlayerHp = maxPlayerHp;
    }

    public GameInfo(Player player) {
        idStack = player.getEventStack();
        playerData = player.getImportantData();
        eventToStart = player.getCurrentEvent();
        this.items = new ArrayList<String>();
        for(Item i: player.getInventory().getItems())
            this.items.add(i.getId());
        this.weaponId = player.getInventory().getWeapon() != null ? player.getInventory().getWeapon().getId() : "";
        this.suitId = player.getInventory().getSuit() != null ? player.getInventory().getSuit().getId() : "";
        this.accessoryId = player.getInventory().getAccessory() != null ? player.getInventory().getAccessory().getId() : "";
        this.playerAttributes = player.getAttributes();
        this.playerHp = player.getHp();
        this.maxPlayerHp = player.getMaxHp();
    }

    public Stack<String> getIdStack() { return idStack; }

    public HashMap<String, String> getPlayerData() {
        return playerData;
    }

    public int getPlayerHp() { return playerHp; }

    public int getMaxPlayerHp() { return maxPlayerHp; }

    public String getEventToStart() {
        return eventToStart;
    }

    public ArrayList<String> getPlayerItems() {
        return this.items;
    }

    public HashMap<String, Integer> getPlayerAttributes() {
        return this.playerAttributes;
    }

    public String getPlayerSuitId() {
        return this.suitId;
    }

    public String getPlayerWeaponId() {
        return this.weaponId;
    }

    public String getPlayerAccessoryId() {
        return this.accessoryId;
    }
}
