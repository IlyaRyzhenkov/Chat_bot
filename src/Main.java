import EventStorage.EventStorage;
import EventStorage.ILoader;
import Game.Game;
import Game.ConsoleIO;
import SaveLoader.AbstractSaveLoader;
import SaveLoader.ISaveLoader;
import SaveLoader.JSONsaveLoader;
import TelegramBot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        if (false) {
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
        game.startGameAtID("Main menu/menu", console);
    }

    private static void runTelegram() {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            ILoader storage = new EventStorage();
            ISaveLoader loader = new AbstractSaveLoader();
            Bot bot = new Bot();
            Game game = new Game(bot, storage, loader);
            bot.setGame(game);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
