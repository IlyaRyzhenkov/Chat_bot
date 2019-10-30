package Game;
import Event.*;
import EventStorage.ILoader;
import Player.Player;
import SaveLoader.GameInfo;
import SaveLoader.ISaveLoader;

import java.util.Stack;

public class Game {
    private IOInterface console;
    private ILoader storage;
    private ISaveLoader save_loader;
    private Event currentEvent;
    private boolean isGameRunning;
    private Stack<String> parentIDs;
    private final Player player;

    public Game(IOInterface io, ILoader loader, ISaveLoader save_loader){
        console = io;
        storage = loader;
        this.save_loader = save_loader;
        isGameRunning = false;
        this.parentIDs = new Stack<String>();
        this.player = new Player();
    }

    public void startGameAtID(String id){
        isGameRunning = true;
        currentEvent = storage.getEventById(id, this.player.getImportantData());
        String nextEventId = "";
        while(isGameRunning){

            sendEventInfo(currentEvent);
            String reply = getReply();

            if(currentEvent.isImportant()) {
                player.remember(currentEvent.getId(), reply);
            }

            if (!handleMessage(reply)) {
                nextEventId = currentEvent.reply(reply);
            }
            if(nextEventId.isEmpty()) {
                nextEventId = parentIDs.pop();
            }
            Event nextEvent = storage.getEventById(nextEventId, this.player.getImportantData());
            if(nextEvent == null){
                break;
            }
            if((currentEvent.isParent())||(nextEvent.getClass() == ExceptionEvent.class)) {
                this.parentIDs.push(currentEvent.getId());
            }
            currentEvent = nextEvent;
        }
    }

    private boolean handleMessage(String message){
        switch (message.toLowerCase()) {
            case ("save"):
                SaveGame();
                return true;

            case ("load"):
                LoadGame();
                return true;

            case("exit"):
                stopGame();
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

    private void SaveGame(){
        console.sendMessage("Введите имя файла");
        String filename = console.getMessage();
        GameInfo info = new GameInfo(parentIDs, player.getImportantData(), currentEvent.getId());
        save_loader.saveGame(filename, info);
    }

    private void LoadGame(){
        console.sendMessage("Введите имя файла");
        String filename2 = console.getMessage();
        GameInfo info = save_loader.loadGame(filename2);
        if (info != null) {
            parentIDs = info.getIDstack();
            player.setImportantData(info.getPlayerData());
            startGameAtID(info.getEventToStart());
        }
    }
}
