package Game;

import java.util.ArrayList;

public class Test1IO implements OInterface, ConsoleInInterface {

    public ArrayList<String> received_replies;
    private final ArrayList<String> messages;
    private int index = 0;

    public Test1IO(ArrayList<String> messages) {
        this.messages = messages;
        received_replies = new ArrayList<String>();
    }

    public void sendMessage(Message message) {
        received_replies.add(message.getMessage());
    }

    public Message getMessage() {
        return index < messages.size() ? new Message("player", messages.get(index++)) : null;
    }
}
