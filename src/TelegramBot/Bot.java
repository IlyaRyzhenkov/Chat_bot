package TelegramBot;

import Game.Game;
import Game.OInterface;
import Game.Message;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot implements OInterface {

    private String botUsername = "qaqaqaqaqaqaqabot";
    private String botToken = "1018946407:AAFmEI4OIN_OOL0O3chCqgGl7FCrsKRHQEU";
    private Game game;

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        Message messageToSend = new Message(update.getMessage().getChatId().toString(), update.getMessage().getText());
        game.makeEventIteration(messageToSend);
    }

    private synchronized void sendMsg(String chatId, String s) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(s);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.print("error sending message");
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMessage(Message message) {
        sendMsg(message.getPlayer(), message.getMessage());
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
