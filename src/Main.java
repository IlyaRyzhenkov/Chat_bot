import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        System.out.println("Tell me your name, stranger.");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + ". Just wait for some time. The program will be continued...");

    }
}
