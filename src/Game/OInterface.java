package Game;

import Event.EventInfo;

public interface OInterface {
    void sendMessage(Message message);

    void sendEvent(String player, EventInfo info);
}
