package Event;

import java.util.ArrayList;

public class EventInfo {
    private final EventType type;
    private final String text;
    private final Answer[] answers;

    public EventInfo(EventType type, String text, Answer[] answers) {
        this.type = type;
        this.text = text;
        this.answers = answers;
    }

    public EventType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Answer[] getAnswers() {
        return answers;
    }
}
