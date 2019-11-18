import Checker.EldritchHorrorChecker;
import Checker.iChecker;
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
        if (args.length < 1) {
            System.err.print("No run parameters, use -c to run console, or -t to run tg-bot");
            System.exit(1);
        }
        if (args[0].equals("-c")) {
            runConsole();
        }
        if (args[0].equals("-t")) {
            runTelegram();
        }
        else {
            System.err.print("Wrong run parameters");
            System.exit(2);
        }
    }

    private static void runConsole() {
        ConsoleIO console = new ConsoleIO();
        ILoader storage = new EventStorage();
        ISaveLoader loader = new JSONsaveLoader();
        iChecker checker = new EldritchHorrorChecker();
        Game game = new Game(console, storage, loader, checker);
        game.startGameAtID("Main menu/menu", console);
    }

    private static void runTelegram() {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            ILoader storage = new EventStorage();
            ISaveLoader loader = new JSONsaveLoader();
            iChecker checker = new EldritchHorrorChecker();
            Bot bot = new Bot();
            Game game = new Game(bot, storage, loader, checker);
            bot.setGame(game);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
