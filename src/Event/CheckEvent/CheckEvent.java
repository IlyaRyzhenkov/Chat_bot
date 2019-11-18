package Event.CheckEvent;

import Event.Event;
import Event.Answer;

public class CheckEvent extends Event {

    private String attribute;
    private int difficulty;

    public CheckEvent(String id, String name, String text, String attribute, int difficulty, Answer[] answers, boolean isImportant) {
        super(id, name, text, answers, isImportant, false);
        this.attribute = attribute;
        this.difficulty = difficulty;
    }
    public String getAttribute() {
        return attribute;
    }

    public int getDifficulty() { return difficulty; }

    @Override
    public String reply(String reply) {
        switch (reply) {
            case "y": return this.getAnswers()[0].getId();
            case "n": return this.getAnswers()[1].getId();
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
