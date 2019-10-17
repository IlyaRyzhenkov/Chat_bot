import EventStorage.EventStorage;
import EventStorage.ILoader;
import Game.Game;
import Game.ConsoleIO;
import SaveLoader.ISaveLoader;
import SaveLoader.JSONsaveLoader;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        ConsoleIO console = new ConsoleIO();
        ILoader storage = new EventStorage();
        ISaveLoader loader = new JSONsaveLoader();

        Game game = new Game(console, storage, loader);
        game.startGameAtID("Main menu/menu");
    }
}
