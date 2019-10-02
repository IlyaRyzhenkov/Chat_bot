import Game.Game;
import Game.ConsoleIO;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException{
        ConsoleIO console = new ConsoleIO();
        Game game = new Game(console);
        game.startGameAtID("1");
    }
}
