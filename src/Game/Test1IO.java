package Game;

import java.util.ArrayList;

public class Test1IO implements IOinterface{

    public ArrayList<String> recived_replies;
    private final ArrayList<String> messages;
    private int index = 0;

    public Test1IO(ArrayList<String> messages){
        this.messages = messages;
        recived_replies = new ArrayList<String>();
    }

    public void sendMessage(String str) {
        recived_replies.add(str);
    }

    public String getMessage() {
        return index < messages.size() ? messages.get(index++) : null;
    }
}
