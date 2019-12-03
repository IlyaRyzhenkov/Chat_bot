package Game;
import Event.Answer;
import Event.EventInfo;

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
        for (Answer answer : info.getAnswers()) {
            System.out.println(answer.getText());
        }
    }

    public Message getMessage(){
        return new Message("player", scanner.nextLine());
    }
}
