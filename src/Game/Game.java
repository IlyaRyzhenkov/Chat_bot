package Game;
import Event.*;
import EventStorage.ILoader;
import Player.Player;

import java.util.Stack;

public class Game {
    private IOInterface console;
    private ILoader storage;
    private Event currentEvent;
    private final Stack<String> parentIDs;
    private final Player player;

    public Game(IOInterface io, ILoader loader){
        console = io;
        storage = loader;
        this.parentIDs = new Stack<String>();
        this.player = new Player();
    }

    public void startGameAtID(String id){
        currentEvent = storage.getEventById(id);
        while(true){

            sendEventInfo(currentEvent);
            String reply = getReply();

            if(currentEvent.isImportant()) {
                player.remember(currentEvent.getId(), reply);
            }
            String nextEventId = currentEvent.reply(reply);
            if(nextEventId.isEmpty()) {
                nextEventId = parentIDs.pop();
            }
            Event nextEvent = storage.getEventById(nextEventId);
            if(nextEvent == null){
                break;
            }
            if((currentEvent.isParent())||(nextEvent.getClass() == ExceptionEvent.class)) {
                this.parentIDs.push(currentEvent.getId());
            }
            currentEvent = nextEvent;
        }
    }

    private void sendEventInfo(Event event){
        console.sendMessage(event.toString());
    }

    private String getReply(){
        return console.getMessage();
    }
}
