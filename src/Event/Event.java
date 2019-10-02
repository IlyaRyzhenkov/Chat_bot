package Event;
import EventStorage.EventStorage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public final class Event {

    private final String id;
    private final String name;
    private final String text;
    public final Answer[] answers;

    public Event(String id, String name, String text, Answer[] answers) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.answers = answers;
    }

    public Event reply(int n) throws IOException, ParseException{
        if ((this.answers.length >= n) && (n > 0))
           return EventStorage.getEventById(this.answers[n - 1].nextId);
        return createIncorrectReplyEvent();
    }

    public String getText(){
        return text;
    }

    private Event createIncorrectReplyEvent(){
        Event newEvent = new Event("100000", "Incorrect Reply", "incorrect reply", answers);
        return newEvent;
    }
}
