package Game;
import AlternativeInteractionControllers.FightController.Arena;
import AlternativeInteractionControllers.FightController.Battle;
import AlternativeInteractionControllers.InventoryController.InventoryController;
import AlternativeInteractionControllers.InventoryController.IInventoryController;
import Checker.IChecker;
import Event.*;
import Event.CheckEvent.CheckEvent;
import Event.ExceptionEvent.ExceptionEvent;
import Storage.IEventStorage;
import Item.Item;
import Item.Accessory;
import Player.Player;
import Player.PlayerState;
import SaveLoader.GameInfo;
import SaveLoader.ISaveLoader;
import Item.Suit;
import Item.Weapon;
import Storage.IItemStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private String initialID = "Main menu/menu";
    private ConsoleInInterface inConsole = null;
    private OInterface console;
    private IEventStorage eventStorage;
    private IItemStorage itemStorage;
    private ISaveLoader saveLoader;
    private IInventoryController inventoryController;
    private Event currentEvent;
    private IChecker checker;
    private Arena arena;
    private HashMap<String, Player> playerTable;

    private boolean isGameRunning;
    private boolean isGameLoaded;

    public Game(OInterface io, IEventStorage loader, ISaveLoader saveLoader, IChecker checker, IItemStorage itemStorage) {
        this.console = io;
        this.inventoryController = new InventoryController();
        this.arena = new Arena();
        this.checker = checker;
        this.itemStorage = itemStorage;
        this.eventStorage = loader;
        this.saveLoader = saveLoader;
        this.isGameRunning = false;
        this.playerTable = new HashMap<String, Player>();
    }

    public void startGameAtID(String id, ConsoleInInterface cons) {
        this.isGameRunning = true;
        this.inConsole = cons;
        createPlayer("player");
        while(this.isGameRunning){
            Message message = inConsole.getMessage();
            makeEventIteration(message);
        }
    }

    public synchronized void makeEventIteration(Message message) {
        String nextEventId = "";
        this.isGameRunning = true;
        this.isGameLoaded = false;
        if (!this.playerTable.containsKey(message.getPlayer())) {
            createPlayer(message.getPlayer());
            return;
        }
        Player player = this.playerTable.get(message.getPlayer());
        this.currentEvent = this.eventStorage.getById(player.getCurrentEvent(), player.getImportantData());
        String reply = message.getMessage();
        if(reply == null)
            return;

        if(this.currentEvent.getClass() == CheckEvent.class) {
            reply = this.checker.check(player.getAttributeDiceSet(((CheckEvent)currentEvent).getAttribute()),
                    ((CheckEvent) this.currentEvent).getDifficulty())
            ? "y" : "n";
        }

        if(this.currentEvent.isImportant()) {
            player.remember(this.currentEvent.getId(), reply);
        }
        boolean isCommand = handleMessage(player, reply);
        if(player.isInventoryOpen()) {
            inventory(player, reply);
            if(player.isInventoryOpen())
                return;
            else {
                sendEvent(player, this.currentEvent);
                return;
            }
        }
        if (!this.isGameRunning) {
            this.isGameRunning = true;
            return;
        }
        if (!isCommand) {
            nextEventId = this.currentEvent.reply(reply);
        }
        else{
            nextEventId = this.currentEvent.getId();
        }

        if(nextEventId.isEmpty()) {
            if(!player.getEventStack().empty()) {
                this.currentEvent = this.eventStorage.getById(player.getEventStack().pop(), player.getImportantData());
                sendEvent(player, this.currentEvent);
                player.setCurrentEvent(this.currentEvent.getId());
            }
            else
                stopGame(player);
            return;
        }

        if(player.getState() == PlayerState.InBattle) {
            inBattle(player, nextEventId);
            return;
        }
        if(nextEventId.compareTo("%arena%") == 0)
            enterTheArena(player);
        Event nextEvent;
        if(player.getState() == PlayerState.InBattle)
            return;
        if(player.getState() == PlayerState.OnArena)
            nextEvent = this.eventStorage.getById("Battle/wait", player.getImportantData());
        else
            nextEvent = this.eventStorage.getById(nextEventId, player.getImportantData());
        if(nextEvent == null){
            return;
        }
        if((this.currentEvent.isParent())||(nextEvent.getClass() == ExceptionEvent.class)) {
            player.getEventStack().push(this.currentEvent.getId());
        }
        if (this.isGameLoaded)
            return;
        sendEvent(player, nextEvent);
        player.setCurrentEvent(nextEventId);
    }

    private boolean inBattle(Player player, String attribute) {
        Battle battle = arena.getBattleByPlayer(player);
        console.sendMessage(new Message(player.getId(), battle.setAttribute(player, attribute)));
        if(battle.isBattlePhaseReady()) {
            battle.fight();
            console.sendMessage(new Message(battle.getDefender().getId(), battle.getDefenderMessage()));
            console.sendMessage(new Message(battle.getAttacker().getId(), battle.getAttackerMessage()));
            if(!battle.getAttacker().isAlive()) {
                stopGame(battle.getAttacker());
                battle.getDefender().setCurrentEvent(battle.getDefender().getEventStack().pop());
                battle.getDefender().setPlayerState(PlayerState.InGame);
                sendEvent(battle.getDefender(), eventStorage.getById(battle.getDefender().getCurrentEvent(), battle.getDefender().getImportantData()));
                return true;
            }
            String s = battle.getDefender().getCurrentEvent();
            battle.getDefender().setCurrentEvent(battle.getAttacker().getCurrentEvent());
            battle.getAttacker().setCurrentEvent(s);
            sendEvent(battle.getAttacker(), eventStorage.getById(battle.getAttacker().getCurrentEvent(), battle.getAttacker().getImportantData()));
            sendEvent(battle.getDefender(), eventStorage.getById(battle.getDefender().getCurrentEvent(), battle.getDefender().getImportantData()));
            return true;
        }
        return false;
    }


    private boolean enterTheArena(Player player) {
        player.setPlayerState(PlayerState.OnArena);

        if (arena.addPlayer(player)) {
            Battle battle = arena.getBattleByPlayer(player);
            battle.getAttacker().setPlayerState(PlayerState.InBattle);
            battle.getDefender().setPlayerState(PlayerState.InBattle);
            battle.getAttacker().setCurrentEvent("Battle/attack");
            battle.getDefender().setCurrentEvent("Battle/defence");
            sendEvent(battle.getAttacker(), eventStorage.getById(battle.getAttacker().getCurrentEvent(), battle.getAttacker().getImportantData()));
            sendEvent(battle.getDefender(), eventStorage.getById(battle.getDefender().getCurrentEvent(), battle.getDefender().getImportantData()));
        }
        return true;
    }

    private boolean handleMessage(Player player, String message) {
        if(player.isInventoryOpen()) return true;
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

            case("/inv"):
                player.openInventory();
                return true;

            default:
                return false;
        }
    }



    private void stopGame(Player player) {
        this.isGameRunning = false;
        this.playerTable.remove(player.getId());
        this.console.sendMessage(new Message(player.getId(), "exit from game"));
        if (this.inConsole != null) {
            System.exit(0);
        }
    }

    private void sendEvent(Player player, Event event) {
        this.console.sendEvent(player.getId(), event.getEventInfo());
    }

    private synchronized void saveGame(Player player, String command) {
        String filename = command.split(" ")[1];
        GameInfo info = new GameInfo(player);
        this.saveLoader.saveGame(filename, info);
        this.console.sendMessage(new Message(player.getId(), "Игра сохранена"));
    }

    private synchronized void inventory(Player player, String message) {
        if (!player.isInventoryOpen()) {
            this.console.sendMessage(new Message(player.getId(),
                    "Inventory opened.\n" + this.inventoryController.getHelpMessage()));
            return;
        }
        String[] command = message.split(" ", 2);
        switch (command[0]) {
            case ("/about"):
                this.console.sendMessage(new Message(player.getId(),
                        this.inventoryController.getItemInfo(player, Integer.parseInt(command[1]))));
                break;
            case ("/inv"):
                this.console.sendInventoryInfo(player.getId(), this.inventoryController.getInventoryInfo(player));
                break;
            case ("/use"):
                this.console.sendMessage(new Message(player.getId(),
                        this.inventoryController.use(player, Integer.parseInt(command[1]))));
                break;
            case ("/equip"):
                this.console.sendMessage(new Message(player.getId(),
                        this.inventoryController.equip(player, Integer.parseInt(command[1]))));
                break;
            case ("/unequip"):
                this.console.sendMessage(new Message(player.getId(),
                        this.inventoryController.unequip(player, command[1])));
                break;
            case ("/help"):
                this.console.sendMessage(new Message(player.getId(),
                        this.inventoryController.getHelpMessage()));
                break;
            case ("/back"):
                player.closeInventory();
                break;
            case ("/item"):
                this.console.sendItemInfo(player.getId(), this.inventoryController.getItemInfoByButton(player, command[1]));
                break;
            default:
                this.console.sendMessage(new Message(player.getId(), "Try again."));
        }
    }

    private synchronized void loadGame(Player play, String command) {
        String filename = command.split(" ")[1];
        GameInfo info = this.saveLoader.loadGame(filename);
        if (info != null) {
            this.isGameLoaded = true;
            ArrayList<Item> items = new ArrayList<Item>();
            for(String id: info.getPlayerItems())
                items.add(this.itemStorage.getById(id));
            Player player = new Player(info.getPlayerAttributes(), info.getPlayerHp(), info.getMaxPlayerHp(), items);
            player.getInventory().setWeapon((Weapon)itemStorage.getById(info.getPlayerWeaponId()));
            player.getInventory().setAccessory((Accessory)itemStorage.getById(info.getPlayerAccessoryId()));
            player.getInventory().setSuit((Suit)itemStorage.getById(info.getPlayerSuitId()));

            player.setEventStack(info.getIdStack());
            player.setImportantData(info.getPlayerData());
            player.setCurrentEvent(info.getEventToStart());
            player.setId(play.getId());
            this.playerTable.put(player.getId(), player);
            this.console.sendMessage(new Message(player.getId(), "Игра загружена"));
            Event lastEvent = this.eventStorage.getById(player.getCurrentEvent(), player.getImportantData());
            sendEvent(player, lastEvent);
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
        this.console.sendMessage(new Message(player.getId(), message));
    }

    private synchronized void createPlayer(String playerID) {
        Player player = new Player(new HashMap<String, Integer>() {
            {
                put("knowledge", 5);
                put("strength", 5);
                put("attention", 5);
                put("communication", 5);
                put("luck", 5);
                put("agility", 5);
            }
        },1, 10,
                new ArrayList<Item>() {
                    {
                        add(itemStorage.getById("Single/AidKit"));
                        add(itemStorage.getById("Outfitted/Suit/Robe"));
                        add(itemStorage.getById("Outfitted/Accessory/BaseKnowledgeBook"));
                        add(itemStorage.getById("Outfitted/Weapon/Screwdriver"));
                    }
                });
        player.setId(playerID);
        player.setCurrentEvent(this.initialID);
        this.playerTable.put(playerID, player);
        this.currentEvent = this.eventStorage.getById(player.getCurrentEvent(), player.getImportantData());
        sendEvent(player, this.currentEvent);
    }

    public HashMap<String, Player> getPlayerTable() {
        return this.playerTable;
    }

    public boolean isGameRunning() {
        return this.isGameRunning;
    }

    public void setInitialID(String initialID) {
        this.initialID = initialID;
    }
}
