package Item;

import Player.Player;

import java.util.HashMap;

public class Suit extends OutfittedItem implements IOutfittedItem {
    public Suit(String id, String name, String info, HashMap<String, Integer> attributes) {
        super(id, name, info, attributes);
    }

    @Override
    public void equip(Player player) {
        if(player.getInventory().getSuit() != null)
            player.getInventory().addItem(player.getInventory().getSuit());
        player.getInventory().setSuit(this);
        player.getInventory().removeItem(this);
    }

    @Override
    public void unequip(Player player) {
        if(player.getInventory().getSuit() != null)
            player.getInventory().addItem(player.getInventory().getSuit());
        player.getInventory().setSuit(null);
    }
}
