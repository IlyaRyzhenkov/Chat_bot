import Game.Game;
import Game.ConsoleIO;

public class Main {

    public static void main(String[] args) {
        ConsoleIO console = new ConsoleIO();
        Game game = new Game(console);
        game.startGameAtID(1);
    }
}
