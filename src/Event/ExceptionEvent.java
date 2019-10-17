package Event;

public class ExceptionEvent extends Event {

    public ExceptionEvent(String id, String name, String text, Answer[] answers) {
        super(id, name, text, answers, false, false);
    }

    @Override
    public String reply(String reply) {
        return "";
    }

}


