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

            sendEventText(current_event);
            sendEventAnswers(current_event);
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
        return new Event("100000", "Incorrect Reply", "incorrect reply", current_event.answers);
    }

    private void sendEventText(Event event){
        console.sendMessage(event.getText());
    }

    private void sendEventAnswers(Event event){
        for(int i = 0; i < event.answers.length; i++)
            console.sendMessage((i+1) + ". " + event.answers[i].text);
    }

    private String getReply(){
        return console.getMessage();
    }
}
