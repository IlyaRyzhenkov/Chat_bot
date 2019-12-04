package AlternativeInteractionControllers.InventoryController;

import Item.Item;
import Player.Player;
import Item.SingleItem;
import Storage.ItemStorage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InventoryControllerTest {

    private ArrayList<Item> itemKit;
    private Player player;
    private InventoryController controller;

    public InventoryControllerTest() {
        ItemStorage storage = new ItemStorage();
        controller = new InventoryController();
        itemKit = new ArrayList<Item>() {
            {
                add(storage.getById("Single/TestItem"));
                add(storage.getById("Outfitted/Suit/TestSuit"));
                add(storage.getById("Outfitted/Weapon/TestWeapon"));
                add(storage.getById("Outfitted/Accessory/TestAccessory"));
            }
        };
        player = new Player(1, 1, 1, 1, 1, 1, 1, itemKit);
    }

    @Test
    public void getItemInfoTest() {
        assertEquals("Test Item info", controller.getItemInfo(player, 1));
        assertEquals("Test Suit info", controller.getItemInfo(player, 2));
        assertEquals("Test Weapon info", controller.getItemInfo(player, 3));
        assertEquals("Test Accessory info", controller.getItemInfo(player, 4));
        assertEquals("Wrong item position. Try again.", controller.getItemInfo(player, 0));
        assertEquals("Wrong item position. Try again.", controller.getItemInfo(player, 5));
    }

    @Test
    public void getInventoryInfo() {
        assertEquals("\nПесронаж:\n" +
                " Здоровье: 1/1\n" +
                " Характеристики:\n" +
                "  Знание: 1\n" +
                "  Сила: 1\n" +
                "  Общение: 1\n" +
                "  Внимание: 1\n" +
                "  Удача: 1\n" +
                "Инвентарь:\n" +
                " Костюм: \n" +
                " Оружие: \n" +
                " Аксессуар: \n" +
                " Вещи: \n" +
                "  1. Test Item(99/99)\n" +
                "  2. Test Suit\n" +
                "  3. Test Weapon\n" +
                "  4. Test Accessory\n", controller.getStringInventoryInfo(player));
    }

    @Test
    public void equipAndUnequip() {
        assertEquals("Item \"Test Suit\" has been equipped.", controller.equip(player, 2));
        assertEquals("Unequipment was successful!", controller.unequip(player, "suit"));
        assertEquals(4, player.getInventory().getItems().size());
        assertEquals("Can't equip this item.", controller.equip(player, 1));
    }

    @Test
    public void useTest() {
        assertEquals("Item \"Test Item(99/99)\" has been used.", controller.use(player, 1));
        assertEquals("Test Item(98/99)", player.getInventory().getItems().get(0).getName());
        while(((SingleItem)player.getInventory().getItems().get(0)).getNumberOfUses() > 1)
            controller.use(player, 1);
        controller.use(player, 1);
        assertEquals(3, player.getInventory().getItems().size());
        assertEquals("Can't use this item.", controller.use(player, 3));
    }
}