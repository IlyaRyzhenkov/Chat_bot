package EventStorage;

import Event.*;
import EventParser.EventParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class EventStorage implements ILoader{
    public Event getEventById(String id)
    {
        if(id.compareTo("exit") == 0)
            return null;
        EventParser parser;
        try {
            parser = new EventParser("events/" + id + ".json");
        } catch (IOException e){ return getEventById("Exceptions/cant_read_file"); }
        catch (ParseException e) { return getEventById("Exceptions/damaged_file"); }
        String type = parser.getType();
        switch (type) {
            case "simple":
                return new SimpleEvent(parser.getID(), parser.getName(), parser.getText(), parser.getAnswers(),
                        parser.isImportant(), parser.isParent());
            case "exception":
                return new ExceptionEvent(parser.getID(), parser.getName(), parser.getText(), parser.getAnswers());

        }
        return getEventById("Exception/damaged_file");
    }
}
