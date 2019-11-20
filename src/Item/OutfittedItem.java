package Item;

import Player.Player;

import java.util.HashMap;

public abstract class OutfittedItem extends Item implements iOutfittedItem {
    private HashMap<String, Integer> attributes;

    public OutfittedItem(String id, String name, String info, HashMap<String, Integer> attributes) {
        super(id, name, info);
        this.attributes = attributes;
    }

    public int getAttribute(String attribute) {
        return this.attributes.getOrDefault(attribute, 0);
    }

    public HashMap<String, Integer> getAttributes() {
        return this.attributes;
    }

    public abstract void equip(Player player);
    public abstract void unequip(Player player);
}
