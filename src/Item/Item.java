package Item;

public abstract class Item implements iItem{
    private int knowledge;
    private int strength;
    private int communication;
    private int attention;
    private int luck;
    private String info;
    private String name;


    public Item(int knowledge, int strength, int communication, int attention, int luck) {
        this.knowledge = knowledge;
        this.strength = strength;
        this.communication = communication;
        this.attention = attention;
        this.luck = luck;
    }


}
