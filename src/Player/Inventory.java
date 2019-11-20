package Player;

import Item.Accessory;
import Item.Item;
import Item.Suit;
import Item.Weapon;

import java.util.ArrayList;

public class Inventory {
    private Weapon weapon;
    private Accessory accessory;
    private Suit suit;
    private ArrayList<Item> items;

    public Inventory(ArrayList<Item> items) {
        this.weapon = null;
        this.suit = null;
        this.accessory = null;
        this.items = new ArrayList<Item>(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Accessory getAccessory() {
        return this.accessory;
    }

    public String getInventoryAttributeDiceSet(String attribute) {
        int n = this.getWeapon().getAttribute(attribute)
                + this.getSuit().getAttribute(attribute)
                + this.getAccessory().getAttribute(attribute);
        return n > 0 ? n + "d6" : "";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Инвентарь:\n Костюм: ");
        builder.append(this.getSuit() == null ? "" : this.getSuit().getName());
        builder.append("\n Оружие: ");
        builder.append(this.getWeapon() == null ? "" : this.getWeapon().getName());
        builder.append("\n Аксессуар: ");
        builder.append(this.getAccessory() == null ? "" : this.getAccessory().getName());
        builder.append("\n Вещи: ");
        for(int i = 0; i < this.items.size(); i++) {
            builder.append("\n  ");
            builder.append(i + 1);
            builder.append(". ");
            builder.append(this.items.get(i).getName());
        }
        return builder.toString();
    }

}
