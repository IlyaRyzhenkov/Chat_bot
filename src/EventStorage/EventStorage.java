package EventStorage;

import Event.*;
import EventParser.EventParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class EventStorage implements ILoader{
    public Event getEventById(String id, HashMap<String, String> playerData)
    {
        if(id.compareTo("exit") == 0)
            return null;
        EventParser parser;
        try {
            parser = new EventParser("events/" + id + ".json");
        } catch (IOException e){ return getEventById("Exceptions/cant_read_file", playerData); }
        catch (ParseException e) { return getEventById("Exceptions/damaged_file", playerData); }
        String type = parser.getType();
        String text = complementTextWithData(parser.getText(), playerData);

        switch (type) {
            case "simple":
                return new SimpleEvent(parser.getID(), parser.getName(), text, parser.getAnswers(),
                        parser.isImportant(), parser.isParent());
            case "any_answer":
                return new AnyAnswerEvent(parser.getID(), parser.getName(), text, parser.getAnswers(),
                        parser.isImportant(), parser.isParent());
            case "exception":
                return new ExceptionEvent(parser.getID(), parser.getName(), text, parser.getAnswers());
        }
        return getEventById("Exception/damaged_file", playerData);
    }

    private static String complementTextWithData(String text, HashMap<String, String> data) {
        StringBuilder result = new StringBuilder();
        StringBuilder id = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '%') {
                if(flag) {
                    result.append(data.getOrDefault(id.toString(), "<" + id.toString() + ">"));
                    id = new StringBuilder();
                }
                flag = !flag;
                continue;
            }
            if(flag)
                id.append(text.charAt(i));
            else
                result.append(text.charAt(i));

        }
        return result.toString();
    }
}
