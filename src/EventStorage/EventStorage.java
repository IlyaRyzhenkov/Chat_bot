package EventStorage;

import EventParser.EventParser;
import Event.Event;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class EventStorage {
    public static Event getEventById(String id)
    {
        return EventParser.parse("events\\" + id + ".json");
    }
    public static Event getEvent()
    {
        return getEventById("1");
    }

}
