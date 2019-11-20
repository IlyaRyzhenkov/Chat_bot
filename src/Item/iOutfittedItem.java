package Item;

import Player.Player;

public interface iOutfittedItem {
    void equip(Player player);
    void unequip(Player player);
    int  getAttribute(String attribute);
}
