package Game;

import Event.Event;
import Storage.IEventStorage;

import java.util.HashMap;
import java.util.Map;


public class TestEventStorage implements IEventStorage {
    private Map<String, Event> events = new HashMap<String, Event>();

    public void addEvent(String id, Event event){
        events.put(id, event);
    }

    public Event getById(String str, HashMap<String, String> data) {
        return events.getOrDefault(str, null);
    }
}
