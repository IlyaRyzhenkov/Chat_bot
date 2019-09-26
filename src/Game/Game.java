package Game;
import Event.Event;
import EventStorage.EventStorage;

public class Game {
    private ConsoleIO console;

    public Game(ConsoleIO consoleIO){
        console = consoleIO;
    }

    public void startGameAtID(int id){
        Event current_event = EventStorage.getEventById(id);
        while(true){
            sendEventText(current_event);
            String reply = getReply();
            current_event = current_event.reply(reply);
        }
    }

    private void sendEventText(Event event){
        console.sendMessage(event.getText());
    }

    private String getReply(){
        return console.getMessage();
    }
}
