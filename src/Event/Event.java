package Event;
import EventStorage.EventStorage;

public final class Event {

    protected final int id;
    public final String name;
    public final String text;
    private final Answer[] answers;

    public Event(int id, String name, String text, Answer[] answers) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.answers = answers;
    }

    public Event reply(String s){
        for (Answer answer : this.answers) {
            if (answer.text.compareTo(s) == 0)
                return EventStorage.getEventById(answer.nextId);
        }
        return createIncorrectReplyEvent();
    }

    public String getText(){
        return text;
    }

    private Event createIncorrectReplyEvent(){
        Event newEvent = new Event(100000, "Incorrect Reply", "incorrect reply", answers);
        return newEvent;
    }
}
