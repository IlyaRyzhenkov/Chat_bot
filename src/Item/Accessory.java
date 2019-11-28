package Item;

import Player.Player;

import java.util.HashMap;

public class Accessory extends OutfittedItem implements IOutfittedItem {
    public Accessory(String id, String name, String info, HashMap<String, Integer> attributes) {
        super(id, name, info, attributes);
    }

    @Override
    public void equip(Player player) {
        if(player.getInventory().getAccessory() != null)
            player.getInventory().addItem(player.getInventory().getAccessory());
        player.getInventory().setAccessory(this);
        player.getInventory().removeItem(this);
    }

    @Override
    public void unequip(Player player) {
        if(player.getInventory().getAccessory() != null)
            player.getInventory().addItem(player.getInventory().getAccessory());
        player.getInventory().setAccessory(null);
    }
}
