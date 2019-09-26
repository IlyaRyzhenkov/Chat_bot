package Game;
import java.util.Scanner;

public class ConsoleIO {
    private Scanner scanner;

    public ConsoleIO(){
        scanner = new Scanner(System.in);
    }

    public void sendMessage(String message){
        System.out.println(message);
    }

    public String getMessage(){
        return scanner.nextLine();
    }
}
