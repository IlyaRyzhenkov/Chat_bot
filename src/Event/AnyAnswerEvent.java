package Event;

public class AnyAnswerEvent extends Event {

    public AnyAnswerEvent(String id, String name, String text, Answer[] answers, boolean isParent) {
        super(id, name, text, answers, true, isParent);
    }

    @Override
    public String reply(String reply) {
        int n;
        try {
            n = Integer.parseInt(reply);
        } catch (NumberFormatException e) {

        }
    }
}
