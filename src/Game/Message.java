package Game;

public class Message {
    private String player;
    private String message;

    public Message(String player, String message) {
        this.player = player;
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
