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

    public Event reply(String s) throws Exception{
        for (Answer answer : this.answers)
            if (answer.text.compareTo(s) == 0)
                return EventStorage.getEventById(answer.nextId);
        throw new Exception("Unknown answer");
    }
}
