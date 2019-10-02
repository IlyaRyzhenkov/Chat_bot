package EventStorage;

import EventParser.EventParser;
import Event.Event;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class EventStorage {
    public static Event getEventById(String id) throws IOException, ParseException
    {
        return EventParser.parse("events\\" + id + ".json");
    }
    public static Event getEvent() throws IOException, ParseException
    {
        return getEventById("1");
    }

}
