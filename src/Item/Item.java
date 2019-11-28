package Item;

public abstract class Item implements IItem {
    private String info;
    private String name;
    private String id;

    public Item(String id, String name, String info) {
        this.name = name;
        this.info = info;
        this.id = id;
    }

    public String getId() { return this.id; }

    public String getInfo() {
        return this.info;
    }

    public String getName() { return  this.name; }

}
