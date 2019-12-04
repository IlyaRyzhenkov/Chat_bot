package AlternativeInteractionControllers.InventoryController;

import Player.Player;
import Player.InventoryInfo;
import Item.ItemInfo;

public interface IInventoryController {
    String getHelpMessage();
    String getItemInfo(Player player, int itemInventoryPosition);
    String getStringInventoryInfo(Player player);
    InventoryInfo getInventoryInfo(Player player);
    ItemInfo getItemInfoByButton(Player player, String id);
    String equip(Player player, int itemInventoryPosition);
    String unequip(Player player, String itemType);
    String use(Player player, int itemInventoryPosition);
}
