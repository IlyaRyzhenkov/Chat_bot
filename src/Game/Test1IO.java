package Game;

import java.util.ArrayList;

public class Test1IO implements IOInterface {

    public ArrayList<String> received_replies;
    private final ArrayList<String> messages;
    private int index = 0;

    public Test1IO(ArrayList<String> messages) {
        this.messages = messages;
        received_replies = new ArrayList<String>();
    }

    public void sendMessage(String str) {
        received_replies.add(str);
    }

    public String getMessage() {
        return index < messages.size() ? messages.get(index++) : null;
    }
}
