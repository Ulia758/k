package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyAmazingBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
class MyAmazingBot extends TelegramLongPollingBot {
    private Random random = new Random();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/randomnumber".equals(messageText)) {
                int randomNumber = getRandomNumber();
                sendTextMessage(chatId, "Случайное число: " + randomNumber);
            } else if ("/rolldice".equals(messageText)) {
                int diceRollResult = rollDice();
                sendTextMessage(chatId, "Результат броска костей: " + diceRollResult);
            } else if (messageText.startsWith("/randomitem ")) {
                String[] words = messageText.split(" ");
                if (words.length > 1) {
                    String randomItem = getRandomItem(words);
                    sendTextMessage(chatId, "Случайный предмет: " + randomItem);
                } else {
                    sendTextMessage(chatId, "Вы не ввели элементы для выбора случайного предмета.");
                }
            } else {
                sendTextMessage(chatId, "Неверная команда. Используйте /randomnumber, /rolldice или /randomitem слово1 слово2 ...");
            }
        }
    }

    private int getRandomNumber() {
        return random.nextInt(100) + 1;
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }

    private String getRandomItem(String[] items) {
        int randomIndex = random.nextInt(items.length - 1) + 1;
        return items[randomIndex];
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return "KolosssssBot";
    }

    @Override
    public String getBotUsername() {
        return "6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM";
    }
}