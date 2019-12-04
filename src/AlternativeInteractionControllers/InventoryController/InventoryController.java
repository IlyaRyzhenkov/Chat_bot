package AlternativeInteractionControllers.InventoryController;

import Item.ItemInfo;
import Item.OutfittedItem;
import Item.SingleItem;
import Player.Player;
import Player.InventoryInfo;
import Player.Inventory;

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
    public String getStringInventoryInfo(Player player) {
        return "\n" + player.toString() + "\n" + player.getInventory().toString() + "\n";
    }

    @Override
    public InventoryInfo getInventoryInfo(Player player) {
        Inventory inventory = player.getInventory();
        return new InventoryInfo(
                player.toString(), inventory.getWeapon(),
                inventory.getSuit(), inventory.getAccessory(),
                inventory.getItems());
    }

    @Override
    public ItemInfo getItemInfoByButton(Player player, String id) {
        if (id.equals("weapon")) {
            return new ItemInfo(player.getInventory().getWeapon(), true, "weapon");
        }
        if (id.equals("suit")) {
            return new ItemInfo(player.getInventory().getSuit(), true, "suit");
        }
        if (id.equals("accessory")) {
            return new ItemInfo(player.getInventory().getAccessory(), true, "accessory");
        }
        try {
            int num = Integer.parseInt(id);
            return new ItemInfo(player.getInventory().getItems().get(num),
                    false, Integer.toString(num + 1));
        } catch (Exception e) {
            System.err.println("Inventory error");
            return null;
        }
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
