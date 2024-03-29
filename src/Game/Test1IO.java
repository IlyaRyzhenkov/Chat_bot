package Game;

import Event.EventInfo;
import Item.ItemInfo;
import Player.InventoryInfo;

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
        String message = info.getText() + '\n';
        for(int i = 0; i < info.getAnswers().length; i++) {
            message += (i + 1) + ". " + info.getAnswers()[i].getText() + "\n";
        }
        receivedReplies.add(message);
    }

    @Override
    public void sendInventoryInfo(String player, InventoryInfo info) {

    }

    @Override
    public void sendItemInfo(String player, ItemInfo info) {

    }

    public Message getMessage() {
        return index < messages.size() ? new Message("player", messages.get(index++)) : null;
    }
}
