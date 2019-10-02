package Game;
import Event.Event;
import EventStorage.EventStorage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Game {
    private ConsoleIO console;

    public Game(ConsoleIO consoleIO){
        console = consoleIO;
    }

    public void startGameAtID(String id)throws IOException, ParseException{
        Event current_event = EventStorage.getEventById(id);
        while(true){
            sendEventText(current_event);
            sendEventAnswers(current_event);
            String reply = getReply();
            current_event = current_event.reply(Integer.parseInt(reply));
        }
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
