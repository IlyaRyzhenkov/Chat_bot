package Storage;

import Event.Event;

import java.util.HashMap;

public interface IEventStorage {
    Event getById(String str, HashMap<String, String> playerData);
}
