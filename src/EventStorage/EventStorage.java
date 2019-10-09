package EventStorage;

import EventParser.EventParser;
import Event.Event;

public class EventStorage implements ILoader{
    public Event getEventById(String id)
    {
        EventParser parser;
        try {
            parser = new EventParser("events\\" + id + ".json");
        } catch (IOException e){ return null; }
        catch (ParseException e) { return null; }
        return new Event(parser.getID(), parser.getName(), parser.getText(), parser.getAnswers());
    }

}
