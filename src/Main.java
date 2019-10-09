import EventStorage.EventStorage;
import EventStorage.ILoader;
import Game.Game;
import Game.ConsoleIO;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        ConsoleIO console = new ConsoleIO();
        ILoader storage = new EventStorage();
        Game game = new Game(console, storage);
        game.startGameAtID("1");
    }
}
