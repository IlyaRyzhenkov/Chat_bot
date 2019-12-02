package Player;

import Item.Item;
import Item.Weapon;
import Item.Accessory;
import Item.Suit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Player {
    private HashMap<String, String> importantData;
    private Stack<String> eventStack;
    private String currentEvent;
    private String id;
    private HashMap<String, Integer> attributes;
    private boolean isInventoryOpen;
    private int hp;
    private int maxHp;
    private Inventory inventory;

    public Player(int knowledge, int strength, int communication, int attention, int luck, int hp, int maxHp, ArrayList<Item> items) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.isInventoryOpen = false;
        this.importantData = new HashMap<String, String>();
        this.eventStack = new Stack<>();
        this.inventory = new Inventory(items);
        this.attributes = new HashMap<String, Integer>() {
            {
                put("knowledge", knowledge);
                put("strength", strength);
                put("communication", communication);
                put("attention", attention);
                put("luck", luck);
            }
        };
    }

    public boolean isInventoryOpen() { return this.isInventoryOpen; }

    public void openInventory() { this.isInventoryOpen = true; }

    public void closeInventory() { this.isInventoryOpen = false; }

    public void hit(int dmg) {
        this.hp = Math.max(0, getHp() - dmg);
    }

    public void heal(int hp) {
        this.hp = Math.min(getMaxHp(), hp + getHp());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getHp() { return this.hp; }

    public int getMaxHp() { return this.maxHp; }

    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public boolean isAlive() {
        return !(this.hp == 0);
    }

    public String getAttributeDiceSet(String attribute) {
        return this.getAttribute(attribute) + "d6" + this.getInventory().getInventoryAttributeDiceSet(attribute);
    }

    public HashMap<String, Integer> getAttributes() {
        return this.attributes;
    }

    public int getAttribute(String attribute) { return attributes.get(attribute); }

    public void setAttribute(String attribute, int value) { this.attributes.put(attribute, value); }

    public HashMap<String, String> getImportantData() {
        return importantData;
    }

    public void setImportantData(HashMap<String, String> data){
        importantData = data;
    }

    public Stack<String> getEventStack() {
        return eventStack;
    }

    public void setEventStack(Stack<String> eventStack) {
        this.eventStack = eventStack;
    }

    public void remember(String id, String answer) {
        this.importantData.put(id, answer);
    }

    public String recall(String id) {
        return importantData.get(id);
    }

    public void setCurrentEvent(String currentEvent) {
        this.currentEvent = currentEvent;
    }

    public String getCurrentEvent() {
        return currentEvent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Песронаж:\n Здоровье: ");
        builder.append(this.getHp());
        builder.append("/");
        builder.append(this.getMaxHp());
        builder.append("\n Характеристики:");
        builder.append("\n  Знание: ");
        HashMap<String, Integer> suitAttrib = this.getInventory().getSuit() == null
                ? new HashMap<String, Integer>()
                : this.getInventory().getSuit().getAttributes();
        HashMap<String, Integer> weaponAttrib = this.getInventory().getWeapon() == null
                ? new HashMap<String, Integer>()
                : this.getInventory().getWeapon().getAttributes();
        HashMap<String, Integer> accessoryAttrib = this.getInventory().getAccessory() == null
                ? new HashMap<String, Integer>()
                : this.getInventory().getAccessory().getAttributes();

        builder.append(this.getAttribute("knowledge")
                + accessoryAttrib.getOrDefault("knowledge", 0)
                + suitAttrib.getOrDefault("knowledge", 0)
                + weaponAttrib.getOrDefault("knowledge", 0));
        builder.append("\n  Сила: ");
        builder.append(this.getAttribute("strength")
                + accessoryAttrib.getOrDefault("strength", 0)
                + suitAttrib.getOrDefault("strength", 0)
                + weaponAttrib.getOrDefault("strength", 0));
        builder.append("\n  Общение: ");
        builder.append(this.getAttribute("communication")
                + accessoryAttrib.getOrDefault("communication", 0)
                + suitAttrib.getOrDefault("communication", 0)
                + weaponAttrib.getOrDefault("communication", 0));
        builder.append("\n  Внимание: ");
        builder.append(this.getAttribute("attention")
                + accessoryAttrib.getOrDefault("attention", 0)
                + suitAttrib.getOrDefault("attention", 0)
                + weaponAttrib.getOrDefault("attention", 0));
        builder.append("\n  Удача: ");
        builder.append(this.getAttribute("luck")
                + accessoryAttrib.getOrDefault("luck", 0)
                + suitAttrib.getOrDefault("luck", 0)
                + weaponAttrib.getOrDefault("luck", 0));
        return builder.toString();
    }
}
