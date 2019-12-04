package TelegramBot;

import Event.Answer;
import Event.EventInfo;
import Event.EventType;
import Item.ItemInfo;
import Player.InventoryInfo;
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
        if (info.getAnswers().length == 0 && info.getType() != EventType.ANYANSWER) {
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
        message.enableMarkdown(true);
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

    @Override
    public void sendInventoryInfo(String player, InventoryInfo info) {
        String weapon = "Weapon: " + (info.getWeapon() == null ? "no equipped" : info.getWeapon().getName());
        String weaponCallback = info.getWeapon() == null ? "/inv" : "/item weapon";
        String suit = "Suit: " + (info.getSuit() == null ? "no equipped" : info.getSuit().getName());
        String suitCallback = info.getSuit() == null ? "/inv" : "/item suit";
        String accessory = "Accessory: " + (info.getAccessory() == null ? "no equipped" : info.getAccessory().getName());
        String accessoryCallback = info.getAccessory() == null ? "/inv" : "/item accessory";

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list2 = new ArrayList<>();

        List<InlineKeyboardButton> weaponList = new ArrayList<>();
        InlineKeyboardButton weaponButton = new InlineKeyboardButton();
        weaponButton.setText(weapon);
        weaponButton.setCallbackData(weaponCallback);
        weaponList.add(weaponButton);
        list2.add(weaponList);

        List<InlineKeyboardButton> suitList = new ArrayList<>();
        InlineKeyboardButton suitButton = new InlineKeyboardButton();
        suitButton.setText(suit);
        suitButton.setCallbackData(suitCallback);
        suitList.add(suitButton);
        list2.add(suitList);

        List<InlineKeyboardButton>accessoryList = new ArrayList<>();
        InlineKeyboardButton accessoryButton = new InlineKeyboardButton();
        accessoryButton.setText(accessory);
        accessoryButton.setCallbackData(accessoryCallback);
        accessoryList.add(accessoryButton);
        list2.add(accessoryList);

        for (int i = 0; i < info.getItems().size(); i++) {
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText((i + 1) + ") " + info.getItems().get(i).getName());
            button.setCallbackData("/item " + i);
            list.add(button);
            list2.add(list);
        }

        List<InlineKeyboardButton> backList = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Back");
        backButton.setCallbackData("/back");
        backList.add(backButton);
        list2.add(backList);

        markup.setKeyboard(list2);
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(player);
        message.setText(info.getPlayerInfo());
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.err.print("error sending message");
        }
    }

    @Override
    public void sendItemInfo(String player, ItemInfo info) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list2 = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();

        String messageText = info.getName() + "\n" + info.getText();
        if (info.isEquipped()) {
            InlineKeyboardButton unequipButton = new InlineKeyboardButton();
            unequipButton.setText("Unequip");
            unequipButton.setCallbackData("/unequip " + info.getSlot());
            list.add(unequipButton);
        }
        if (info.isUsable()) {
            InlineKeyboardButton useButton = new InlineKeyboardButton();
            useButton.setText("Use");
            useButton.setCallbackData("/use " + info.getSlot());
            list.add(useButton);
        }
        if (info.isEquippable() && !info.isEquipped()) {
            InlineKeyboardButton equipButton = new InlineKeyboardButton();
            equipButton.setText("Equip");
            equipButton.setCallbackData("/equip " + info.getSlot());
            list.add(equipButton);
        }
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Back");
        backButton.setCallbackData("/inv");
        list.add(backButton);
        list2.add(list);
        markup.setKeyboard(list2);

        SendMessage message = new SendMessage();
        message.setChatId(player);
        message.enableMarkdown(true);
        message.setText(messageText);
        message.setReplyMarkup(markup);
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
