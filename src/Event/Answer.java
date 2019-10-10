package Event;

public class Answer {
    private final String text;
    private final String nextId;

    public Answer(String text, String nextId) {
        this.text = text;
        this.nextId = nextId;
    }

    public String getText(){
        return text;
    }

    public String getNextId(){
        return nextId;
    }
}
