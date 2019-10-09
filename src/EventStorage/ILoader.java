package EventStorage;

import Event.Event;

public interface ILoader {
    public Event getEventById(String str);
}
