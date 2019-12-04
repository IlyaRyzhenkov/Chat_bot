package Game;

import Event.EventInfo;
import Player.InventoryInfo;
import Item.ItemInfo;

public interface OInterface {
    void sendMessage(Message message);

    void sendEvent(String player, EventInfo info);

    void sendInventoryInfo(String player, InventoryInfo info);

    void sendItemInfo(String player, ItemInfo info);
}
