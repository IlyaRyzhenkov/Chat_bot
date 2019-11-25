package AlternativeInteractionControllers.InventoryController;

import Item.Item;
import Player.Player;

public interface iInventoryController {
    String getHelpMessage();
    String getItemInfo(Player player, int itemInventoryPosition);
    String getInventoryInfo(Player player);
    String equip(Player player, int itemInventoryPosition);
    String unequip(Player player, String itemType);
    String use(Player player, int itemInventoryPosition);
}
