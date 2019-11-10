import EventStorage.EventStorage;
import EventStorage.ILoader;
import Game.Game;
import Game.ConsoleIO;
import SaveLoader.ISaveLoader;
import SaveLoader.JSONsaveLoader;
import TelegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        if (true) {
            runTelegram();
        }
        else {
            runConsole();
        }
    }

    private static void runConsole() {
        ConsoleIO console = new ConsoleIO();
        ILoader storage = new EventStorage();
        ISaveLoader loader = new JSONsaveLoader();

        Game game = new Game(console, storage, loader);
        game.startGameAtID("Main menu/menu");
    }

    private static void runTelegram() {
        ApiContextInitializer.init();
        
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
