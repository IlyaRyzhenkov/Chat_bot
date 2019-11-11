package Player;

import java.util.HashMap;
import java.util.Stack;

public class Player {
    private HashMap<String, String> importantData;
    private Stack<String> eventStack;
    private String currentEvent;
    private String id;

    private int knowledge;
    private int strength;
    private int communication;
    private int attention;
    private int luck;

    public Player() {
        this.importantData = new HashMap<String, String>();
        this.eventStack = new Stack<>();
    }

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
