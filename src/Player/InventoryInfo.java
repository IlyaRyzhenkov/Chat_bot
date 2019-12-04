package Player;

import Item.Item;
import Item.Weapon;
import Item.Suit;
import Item.Accessory;

import java.util.List;

public class InventoryInfo {
    private final List<Item> items;
    private final Weapon weapon;
    private final Suit suit;
    private final Accessory accessory;
    private final String playerInfo;

    public InventoryInfo(String playerInfo, Weapon weapon, Suit suit, Accessory accessory, List<Item> items) {
        this.playerInfo = playerInfo;
        this.weapon = weapon;
        this.suit = suit;
        this.accessory = accessory;
        this.items = items;
    }

    public String getPlayerInfo() {
        return playerInfo;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Suit getSuit() {
        return suit;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public List<Item> getItems() {
        return items;
    }
}
