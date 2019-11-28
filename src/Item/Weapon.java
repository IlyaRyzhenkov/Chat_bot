package Item;

import Player.Player;

import java.util.HashMap;

public class Weapon extends OutfittedItem implements IOutfittedItem {
    public Weapon(String id, String name, String info, HashMap<String, Integer> attributes) {
        super(id, name, info, attributes);
    }

    @Override
    public void equip(Player player) {
        if(player.getInventory().getWeapon() != null)
            player.getInventory().addItem(player.getInventory().getWeapon());
        player.getInventory().setWeapon(this);
        player.getInventory().removeItem(this);
    }

    @Override
    public void unequip(Player player) {
        if(player.getInventory().getWeapon() != null)
            player.getInventory().addItem(player.getInventory().getWeapon());
        player.getInventory().setWeapon(null);
    }
}
