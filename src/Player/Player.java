package Player;

import Item.Item;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Stack;

public class Player {
    private HashMap<String, String> importantData;
    private Stack<String> eventStack;
    private String currentEvent;
    private String id;
    private ArrayList<Item> items;
    private HashMap<String, Integer> attributes;

    public Player(int knowledge, int strength, int communication, int attention, int luck) {
        this.importantData = new HashMap<String, String>();
        this.eventStack = new Stack<>();
        this.items = new ArrayList<Item>();
        attributes = new HashMap<String, Integer>();
        attributes.put("knowledge", knowledge);
        attributes.put("strength", strength);
        attributes.put("communication", communication);
        attributes.put("attention", attention);
        attributes.put("luck", luck);
    }

    public String getAttributeDiceSet(String attribute) {
        return this.getAttribute(attribute) + "d6";
    }

    public int getAttribute(String attribute) { return attributes.get(attribute); }

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
}
