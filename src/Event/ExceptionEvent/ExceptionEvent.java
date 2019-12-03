package Event.ExceptionEvent;

import Event.Event;
import Event.Answer;
import Event.EventType;
import Event.EventInfo;

public class ExceptionEvent extends Event {

    public ExceptionEvent(String id, String name, String text, Answer[] answers) {
        super(id, name, text, answers, false, false);
    }

    @Override
    public String reply(String reply) {
        return "";
    }

    @Override
    public EventInfo getEventInfo() {
        return new EventInfo(EventType.EXCEPTION, getText(), getAnswers());
    }
}


