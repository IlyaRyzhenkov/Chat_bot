package Event;

import java.util.HashMap;

public class Answer {
    private final String text;
    private final String id;
    private final HashMap<String, String> dependencies;

    public Answer(String text, String id, HashMap<String, String> dependencies) {
        this.text = text;
        this.id = id;
        this.dependencies = dependencies;
    }

    public String getText(){
        return text;
    }

    public String getId(){
        return id;
    }

    public HashMap<String, String> getDependencies() { return this.dependencies; }
}
