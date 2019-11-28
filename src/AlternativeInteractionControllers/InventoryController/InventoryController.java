package AlternativeInteractionControllers.InventoryController;

import Item.OutfittedItem;
import Item.SingleItem;
import Player.Player;

public class InventoryController implements IInventoryController {

    @Override
    public String getItemInfo(Player player, int itemInventoryPosition) {
        if(itemInventoryPosition > player.getInventory().getItems().size() || itemInventoryPosition < 1)
            return "Wrong item position. Try again.";
        return player.getInventory().getItems().get(itemInventoryPosition - 1).getInfo();
    }

    @Override
    public String getHelpMessage() {
        return "Use /about <item number> to get more information about some item.\n" +
                "Use /inv to get current state of inventory.\n" +
                "Use /equip <item number> to equip suit/weapon/accessory.\n" +
                "Use /unequip <suit/weapon/accessory> to unequip suit/weapon/accessory respectively.\n" +
                "Use /use <item number> to use this single item." +
                "Use /back to exit from inventory.\n" +
                "Use /help to get this message again.\n";
    }

    @Override
    public String getInventoryInfo(Player player) {
        return "\n" + player.toString() + "\n" + player.getInventory().toString() + "\n";
    }

    @Override
    public String equip(Player player, int itemInventoryPosition) {
        String use;
        try {
            use = "Item \"" + player.getInventory().getItems().get(itemInventoryPosition - 1).getName() + "\" has been equipped.";
            ((OutfittedItem) player.getInventory().getItems().get(itemInventoryPosition - 1)).equip(player);
        } catch (NullPointerException | ClassCastException | IndexOutOfBoundsException e) { return "Can't equip this item."; }
        return use;
    }

    @Override
    public String unequip(Player player, String itemType) {
        switch (itemType){
            case("suit"):
                if(player.getInventory().getSuit() != null)
                    player.getInventory().getSuit().unequip(player);
                break;
            case("weapon"):
                if(player.getInventory().getWeapon() != null)
                    player.getInventory().getWeapon().unequip(player);
                break;
            case("accessory"):
                if(player.getInventory().getAccessory() != null)
                    player.getInventory().getAccessory().unequip(player);
                break;
            default:
                return "Can't unequip it. Try again.";
        }
        return "Unequipment was successful!";
    }

    @Override
    public String use(Player player, int itemInventoryPosition) {
        String use;
        try {
            use = "Item \"" + player.getInventory().getItems().get(itemInventoryPosition - 1).getName() + "\" has been used.";
            ((SingleItem) player.getInventory().getItems().get(itemInventoryPosition - 1)).use(player);
        } catch (NullPointerException | ClassCastException | IndexOutOfBoundsException e) { return "Can't use this item."; }
        return use;
    }


}
