package Game;
import Event.*;
import Event.ExceptionEvent.ExceptionEvent;
import EventStorage.ILoader;
import Player.Player;
import SaveLoader.GameInfo;
import SaveLoader.ISaveLoader;

import java.util.HashMap;

public class Game {
    private String initialID = "Main menu/menu";
    private ConsoleInInterface inConsole = null;
    private OInterface console;
    private ILoader storage;
    private ISaveLoader save_loader;
    private Event currentEvent;
    private HashMap<String, Player> playerTable;

    private boolean isGameRunning;
    private boolean isGameLoaded;

    public Game(OInterface io, ILoader loader, ISaveLoader save_loader) {
        console = io;
        storage = loader;
        this.save_loader = save_loader;
        isGameRunning = false;
        this.playerTable = new HashMap<String, Player>();
    }

    public void startGameAtID(String id, ConsoleInInterface cons) {
        isGameRunning = true;
        inConsole = cons;
        CreatePlayer("player");
        while(isGameRunning){
            Message message = inConsole.getMessage();
            makeEventIteration(message);
        }
    }

    public synchronized void makeEventIteration(Message message) {
        String nextEventId = "";
        isGameRunning = true;
        isGameLoaded = false;
        if (!playerTable.containsKey(message.getPlayer())) {
            CreatePlayer(message.getPlayer());
            return;
        }
        Player player = playerTable.get(message.getPlayer());
        currentEvent = storage.getEventById(player.getCurrentEvent(), player.getImportantData());
        String reply = message.getMessage();
        if(reply == null)
            return;

        if(currentEvent.isImportant()) {
            player.remember(currentEvent.getId(), reply);
        }
        boolean isCommand = handleMessage(player, reply);
        if (!isGameRunning) {
            isGameRunning = true;
            return;
        }
        if (!isCommand) {
            nextEventId = currentEvent.reply(reply);
        }
        else{
            nextEventId = currentEvent.getId();
        }
        if(nextEventId.isEmpty()) {
            if(!player.getEventStack().empty()) {
                currentEvent = storage.getEventById(player.getEventStack().pop(), player.getImportantData());
                sendEventInfo(player, currentEvent);
                player.setCurrentEvent(currentEvent.getId());
            }
            else
                stopGame(player);
            return;
        }
        Event nextEvent = storage.getEventById(nextEventId, player.getImportantData());
        if(nextEvent == null){
            return;
        }
        if((currentEvent.isParent())||(nextEvent.getClass() == ExceptionEvent.class)) {
            player.getEventStack().push(currentEvent.getId());
        }
        if (isGameLoaded)
            return;
        sendEventInfo(player, nextEvent);
        player.setCurrentEvent(nextEventId);
    }

    private boolean handleMessage(Player player, String message) {
        switch (message.toLowerCase().split(" ")[0]) {
            case ("/save"):
                saveGame(player, message);
                return true;

            case ("/load"):
                loadGame(player, message);
                return true;

            case("/exit"):
                stopGame(player);
                return true;

            case("/help"):
                sendHelpMessage(player);
                return true;

            default:
                return false;
        }
    }

    private void stopGame(Player player) {
        isGameRunning = false;
        playerTable.remove(player.getId());
        console.sendMessage(new Message(player.getId(), "exit from game"));
        if (inConsole != null) {
            System.exit(0);
        }
    }

    private void sendEventInfo(Player player, Event event) {
        console.sendMessage(new Message(player.getId(), event.toString()));
    }

    private synchronized void saveGame(Player player, String command) {
        String filename = command.split(" ")[1];
        GameInfo info = new GameInfo(player);
        save_loader.saveGame(filename, info);
        console.sendMessage(new Message(player.getId(), "Игра сохранена"));
    }

    private synchronized void loadGame(Player play, String command) {
        String filename2 = command.split(" ")[1];
        GameInfo info = save_loader.loadGame(filename2);
        if (info != null) {
            isGameLoaded = true;
            Player player = new Player();
            player.setEventStack(info.getIDstack());
            player.setImportantData(info.getPlayerData());
            player.setCurrentEvent(info.getEventToStart());
            player.setId(play.getId());
            playerTable.put(player.getId(), player);
            console.sendMessage(new Message(player.getId(), "Игра загружена"));
            Event lastEvent = storage.getEventById(player.getCurrentEvent(), player.getImportantData());
            sendEventInfo(player, lastEvent);
        }
    }

    private void sendHelpMessage(Player player) {
        String message = "Story-game bot\n" +
                "You can win or lose by answering bots questions.\n" +
                "You can save the current state of game by '/save <<filename>>' command.\n" +
                "You can load the game state by '/load <<filename>>' command.\n" +
                "You can stop the game by '/exit' command.\n" +
                "You should'nt answering the questions by this commands and the 'default' word.\n" +
                "Good luck and have fun!";
        console.sendMessage(new Message(player.getId(), message));
    }

    private synchronized void CreatePlayer(String playerID) {
        Player player = new Player();
        player.setId(playerID);
        player.setCurrentEvent(initialID);
        playerTable.put(playerID, player);
        currentEvent = storage.getEventById(player.getCurrentEvent(), player.getImportantData());
        sendEventInfo(player, currentEvent);
    }

    public HashMap<String, Player> getPlayerTable() {
        return playerTable;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setInitialID(String initialID) {
        this.initialID = initialID;
    }
}
