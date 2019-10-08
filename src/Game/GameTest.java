package Game;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void startGameAtID() {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("1");
        IOinterface io = new Test1IO(messages);
        Game game = new Game(io);
        game.startGameAtID("test1");
    }
}