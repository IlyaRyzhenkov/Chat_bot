package EventStorage;

import EventParser.EventParser;
import Event.Event;

public class EventStorage implements ILoader{
    public Event getEventById(String id)
    {
        return EventParser.parse("events\\" + id + ".json");
    }
    public Event getEvent()
    {
        return getEventById("1");
    }

}
