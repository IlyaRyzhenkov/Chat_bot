package Event;

public final class Event {

    private final String id;
    private final String name;
    private final String text;
    public final Answer[] answers;

    public Event(String id, String name, String text, Answer[] answers) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.answers = answers;
    }

    public String reply(String reply){
        int n;
        try {
            n = Integer.parseInt(reply);
        } catch (NumberFormatException e){
            return "Incorrect";
        }
        if ((this.answers.length >= n) && (n > 0))
           return this.answers[n - 1].nextId;
        return "Incorrect";
    }

    public String getText(){
        return text;
    }
}
