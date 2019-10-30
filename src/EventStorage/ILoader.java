package EventStorage;

import Event.Event;

import java.util.HashMap;

public interface ILoader {
    public Event getEventById(String str, HashMap<String, String> playerData);
}
