package Event;

public final class SimpleEvent extends Event {

    public SimpleEvent(String id, String name, String text, Answer[] answers, boolean isImportant, boolean isParent) {
        super(id, name, text, answers, isImportant, isParent);
    }

    @Override
    public String reply(String reply) {
        int n;
        try {
            n = Integer.parseInt(reply);
        } catch (NumberFormatException e){
            return "Exceptions/incorrect_reply";
        }
        if ((this.getAnswers().length >= n) && (n > 0))
           return this.getAnswers()[n - 1].getId();
        return "Exceptions/incorrect_reply";
    }

}
