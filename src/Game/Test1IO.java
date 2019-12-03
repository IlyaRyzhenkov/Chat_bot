package Game;

import Event.EventInfo;

import java.util.ArrayList;

public class Test1IO implements OInterface, ConsoleInInterface {

    private ArrayList<String> receivedReplies;
    private final ArrayList<String> messages;
    private int index = 0;

    public ArrayList<String> getReceivedReplies() { return this.receivedReplies; }

    public Test1IO(ArrayList<String> messages) {
        this.messages = messages;
        receivedReplies = new ArrayList<String>();
    }

    public void sendMessage(Message message) {
        receivedReplies.add(message.getMessage());
    }

    @Override
    public void sendEvent(String player, EventInfo info) {

    }

    public Message getMessage() {
        return index < messages.size() ? new Message("player", messages.get(index++)) : null;
    }
}
