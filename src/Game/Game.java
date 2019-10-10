package Game;
import Event.Event;
import EventStorage.ILoader;

public class Game {
    private IOinterface console;
    private ILoader storage;
    private Event current_event;

    public Game(IOinterface io, ILoader loader){
        console = io;
        storage = loader;
    }

    public void startGameAtID(String id){
        current_event = storage.getEventById(id);
        while(true){

            if(current_event == null){
                break;
            }

            sendEventInfo(current_event);
            String reply = getReply();
            String next_event_id = current_event.reply(reply);

            if(next_event_id.equals("Incorrect")){
                current_event = createIncorrectReplyEvent();
            }
            else {
                current_event = storage.getEventById(next_event_id);
            }
        }
    }

    private Event createIncorrectReplyEvent(){
        return new Event("100000", "Incorrect Reply", "incorrect reply", current_event.getAnswers());
    }

    private void sendEventInfo(Event event){
        console.sendMessage(event.toString());
    }

    private String getReply(){
        return console.getMessage();
    }
}
