package Game;
import Event.Answer;
import Event.EventInfo;
import Item.ItemInfo;
import Player.InventoryInfo;

import java.util.Scanner;

public class ConsoleIO implements OInterface, ConsoleInInterface {
    private Scanner scanner;

    public ConsoleIO(){
        scanner = new Scanner(System.in);
    }

    public void sendMessage(Message message){
        System.out.println(message.getMessage());
    }

    @Override
    public void sendEvent(String player, EventInfo info) {
        System.out.println(info.getText());
        for (int i = 0; i < info.getAnswers().length; i++) {
            System.out.println((i + 1) + ". " + info.getAnswers()[i].getText());
        }
    }

    @Override
    public void sendInventoryInfo(String player, InventoryInfo info) {
        System.out.println(info.getPlayerInfo());
        System.out.println("Weapon: " + (info.getWeapon() == null ? "" : info.getWeapon().getName()));
        System.out.println("Suit: " + (info.getSuit() == null ? "" : info.getSuit().getName()));
        System.out.println("Accessory: " + (info.getAccessory() == null ? "" : info.getAccessory().getName()));
        for (int i = 0; i < info.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + info.getItems().get(i).getName());
        }
    }

    @Override
    public void sendItemInfo(String player, ItemInfo info) {

    }

    public Message getMessage(){
        return new Message("player", scanner.nextLine());
    }
}
