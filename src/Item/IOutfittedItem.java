package Item;

import Player.Player;

public interface IOutfittedItem {
    void equip(Player player);
    void unequip(Player player);
    int  getAttribute(String attribute);
}
