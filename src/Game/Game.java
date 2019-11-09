package Game;
import Event.*;
import Event.ExceptionEvent.ExceptionEvent;
import EventStorage.ILoader;
import Player.Player;
import SaveLoader.GameInfo;
import SaveLoader.ISaveLoader;

import java.util.HashMap;
import java.util.Stack;

public class Game {
    private IOInterface console;
    private ILoader storage;
    private ISaveLoader save_loader;
    private Event currentEvent;
    private boolean isGameRunning;
    private Stack<String> parentIDs;
    private final Player player;

    public Game(IOInterface io, ILoader loader, ISaveLoader save_loader) {
        console = io;
        storage = loader;
        this.save_loader = save_loader;
        isGameRunning = false;
        this.parentIDs = new Stack<String>();
        this.player = new Player();
    }

    public void startGameAtID(String id) {
        isGameRunning = true;
        currentEvent = storage.getEventById(id, this.player.getImportantData());
        while(isGameRunning){
            makeEventIteration();
        }
    }

    public void makeEventIteration() {
        String nextEventId = "";

        sendEventInfo(currentEvent);
        String reply = getReply();
        if(reply == null)
            return;

        if(currentEvent.isImportant()) {
            player.remember(currentEvent.getId(), reply);
        }
        boolean isCommand = handleMessage(reply);
        if (!isCommand) {
            nextEventId = currentEvent.reply(reply);
        }
        else{
            nextEventId = currentEvent.getId();
        }
        if(nextEventId.isEmpty()) {
            if(!parentIDs.empty())
                currentEvent = storage.getEventById(parentIDs.pop(), this.player.getImportantData());
            else
                stopGame();
            return;
        }
        Event nextEvent = storage.getEventById(nextEventId, this.player.getImportantData());
        if(nextEvent == null){
            return;
        }
        if((currentEvent.isParent())||(nextEvent.getClass() == ExceptionEvent.class)) {
            this.parentIDs.push(currentEvent.getId());
        }
        currentEvent = nextEvent;
    }

    public Stack<String> getParentIDs(){
        return parentIDs;
    }

    public HashMap<String, String> getPlayerData() {
        return player.getImportantData();
    }

    private boolean handleMessage(String message) {
        switch (message.toLowerCase()) {
            case ("/save"):
                saveGame();
                return true;

            case ("/load"):
                loadGame();
                return true;

            case("/exit"):
                stopGame();
                return true;

            case("/help"):
                sendHelpMessage();
                return true;

            default:
                return false;
        }
    }

    private void stopGame(){
        isGameRunning = false;
    }

    private void sendEventInfo(Event event){
        console.sendMessage(event.toString());
    }

    private String getReply(){
        return console.getMessage();
    }

    private void saveGame() {
        console.sendMessage("Введите имя файла");
        String filename = console.getMessage();
        GameInfo info = new GameInfo(parentIDs, player.getImportantData(), currentEvent.getId());
        save_loader.saveGame(filename, info);
    }

    private void loadGame() {
        console.sendMessage("Введите имя файла");
        String filename2 = console.getMessage();
        GameInfo info = save_loader.loadGame(filename2);
        if (info != null) {
            parentIDs = info.getIDstack();
            player.setImportantData(info.getPlayerData());
            startGameAtID(info.getEventToStart());
        }
    }

    private void sendHelpMessage() {
        String message = "Story-game bot\n" +
                "You can win or lose by answering bots questions.\n" +
                "You can save the current state of game by '/save' command.\n" +
                "You can load the game state by '/load' command.\n" +
                "You can stop the game by '/exit' command.\n" +
                "You should`nt answering the questions by this commands and the 'default' word.\n" +
                "Good luck and have fun!";
        console.sendMessage(message);
    }

    public Player getPlayer() {
        return this.player;
    }
}
