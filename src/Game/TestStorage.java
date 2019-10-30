package Game;

import Event.SimpleEvent;
import EventStorage.ILoader;

import java.util.HashMap;
import java.util.Map;


public class TestStorage implements ILoader {
    private Map<String, SimpleEvent> events = new HashMap<String, SimpleEvent>();

    public void addEvent(String id, SimpleEvent event){
        events.put(id, event);
    }

    public SimpleEvent getEventById(String str, HashMap<String, String> data) {
        return events.getOrDefault(str, null);
    }
}
