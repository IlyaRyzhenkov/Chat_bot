package Game;
import java.util.Scanner;

public class ConsoleIO implements OInterface, ConsoleInInterface {
    private Scanner scanner;

    public ConsoleIO(){
        scanner = new Scanner(System.in);
    }

    public void sendMessage(Message message){
        System.out.println(message.getMessage());
    }

    public Message getMessage(){
        return new Message("player", scanner.nextLine());
    }
}
