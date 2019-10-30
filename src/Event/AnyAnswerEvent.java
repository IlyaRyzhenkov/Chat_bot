package Event;

public class AnyAnswerEvent extends Event {

    public AnyAnswerEvent(String id, String name, String text, Answer[] answers, boolean isImportant, boolean isParent) {
        super(id, name, text, answers, isImportant, isParent);
    }

    @Override
    public String reply(String reply) {
        Answer[] answers = this.getAnswers();
        String lowerReply = reply.toLowerCase();
        String defaultID = "";
        for (Answer answer: answers) {
            if(answer.getText().toLowerCase().compareTo("default") == 0) {
                defaultID = answer.getId();
                continue;
            }
            if(answer.getText().toLowerCase().compareTo(lowerReply) == 0)
                return answer.getId();
        }
        return defaultID;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
