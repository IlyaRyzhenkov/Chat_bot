package Item;

public class ItemInfo {
    private final String text;
    private final String id;
    private final String name;
    private final String slot;
    private final boolean isEquipped;
    private final boolean isUsable;
    private final boolean isEquippable;

    public ItemInfo(Item item, boolean isEquipped, String slot) {
        this.text = item.getInfo();
        this.id = item.getId();
        this.name = item.getName();
        this.isUsable = item.isUsable();
        this.isEquippable = item.isEquippable();
        this.isEquipped = isEquipped;
        this.slot = slot;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getSlot() {
        return slot;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public boolean isEquippable() {
        return isEquippable;
    }
}
