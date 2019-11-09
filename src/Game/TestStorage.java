package Game;

import Event.Event;
import EventStorage.ILoader;

import java.util.HashMap;
import java.util.Map;


public class TestStorage implements ILoader {
    private Map<String, Event> events = new HashMap<String, Event>();

    public void addEvent(String id, Event event){
        events.put(id, event);
    }

    public Event getEventById(String str, HashMap<String, String> data) {
        return events.getOrDefault(str, null);
    }
}
