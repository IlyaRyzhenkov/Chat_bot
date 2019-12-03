package TelegramBot;

import Event.Answer;
import Event.EventInfo;
import Game.Game;
import Game.OInterface;
import Game.Message;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot implements OInterface {

    private String botUsername = "qaqaqaqaqaqaqabot";
    private String botToken = "1018946407:AAFmEI4OIN_OOL0O3chCqgGl7FCrsKRHQEU";
    private Game game;

    @Override
    public void onUpdateReceived(Update update) {
        String message;
        String chatId;
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        }
        else {
            message = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
        }
        Message messageToSend = new Message(chatId, message);
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
            e.printStackTrace();
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

    @Override
    public void sendEvent(String player, EventInfo info) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list2 = new ArrayList<>();
        for (int i = 0; i < info.getAnswers().length; i++) {
            Answer answer = info.getAnswers()[i];
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(answer.getText());
            button.setCallbackData(Integer.toString(i + 1));
            list.add(button);
            list2.add(list);
        }
        if (info.getAnswers().length == 0) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Подожать..");
            button.setCallbackData("1");
            List<InlineKeyboardButton> list = new ArrayList<>();
            list.add(button);
            list2.add(list);
        }
        else {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Инвентарь");
            button.setCallbackData("/inv");
            List<InlineKeyboardButton> list = new ArrayList<>();
            list.add(button);
            list2.add(list);
        }
        keyboardMarkup.setKeyboard(list2);
        SendMessage message = new SendMessage();
        message.setChatId(player);
        message.setText(info.getText());
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.err.print("error sending message");
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
