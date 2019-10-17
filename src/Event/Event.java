package Event;

public abstract class Event {
    private final String id;
    private final String name;
    private final String text;
    private final Answer[] answers;
    private final boolean isImportant;
    private final boolean isParent;

    public Event(String id, String name, String text, Answer[] answers, boolean isImportant, boolean isParent) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.answers = answers;
        this.isImportant = isImportant;
        this.isParent = isParent;
    }

    public abstract String reply(String reply);

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(text).append('\n');
        for(int i = 0; i < answers.length; i++)
            builder.append(i + 1).append(". ").append(answers[i].getText()).append('\n');
        return builder.toString();
    }

    public String getText() { return this.text; }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public boolean isImportant() { return this.isImportant; }

    public boolean isParent() {return this.isParent;}

    public Answer[] getAnswers(){ return this.answers; }
}
