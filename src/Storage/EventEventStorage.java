package Storage;

import Event.Answer;
import Event.CheckEvent.CheckEvent;
import Event.Event;
import Event.AnyAnswerEvent.AnyAnswerEvent;
import Event.ExceptionEvent.ExceptionEvent;
import Event.SimpleEvent.SimpleEvent;
import Parser.EventParser.EventParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EventEventStorage implements IEventStorage {
    public Event getById(String id, HashMap<String, String> playerData) {
        if(id.compareTo("exit") == 0)
            return null;
        EventParser parser;
        try {
            parser = new EventParser("events/" + id + ".json");
        } catch (IOException e) { return getById("Exceptions/cant_read_file", playerData); }
        catch (ParseException e) { return getById("Exceptions/damaged_file", playerData); }
        String type = parser.getType();
        String text = complementTextWithData(parser.getText(), playerData);
        Answer[] answers = filterAnswersWithData(parser.getAnswers(), playerData);

        switch (type) {
            case "check":
                return new CheckEvent(parser.getID(), parser.getName(), parser.getText(), parser.getAttribute(),
                        parser.getDifficulty(), answers, parser.isImportant());
            case "simple":
                return new SimpleEvent(parser.getID(), parser.getName(), text, answers, parser.isImportant(),
                        parser.isParent());
            case "any_answer":
                return new AnyAnswerEvent(parser.getID(), parser.getName(), text, answers, parser.isImportant(),
                        parser.isParent());
            case "exception":
                return new ExceptionEvent(parser.getID(), parser.getName(), text, answers);
        }
        return getById("Exception/damaged_file", playerData);
    }

    private Answer[] filterAnswersWithData(Answer[] answers, HashMap<String, String> data) {
        ArrayList<Answer> result = new ArrayList<Answer>();
        for(Answer answer: answers) {
            boolean flag = true;
            HashMap<String, String> dependencies = answer.getDependencies();
            for(String key: dependencies.keySet()) {
                if(!((data.containsKey(key))&&(data.get(key).compareTo(dependencies.get(key)) == 0)))
                    flag = false;
            }
            if (flag)
                result.add(answer);
        }
        return result.toArray(new Answer[result.size()]);
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
