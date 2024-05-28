package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.security.SecureRandom;
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

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (messageText.equals("/generate")) {
                int length = 10; // default password length
                String generatedPassword = generatePassword(length);
                sendTextMessage(update.getMessage().getChatId().toString(), generatedPassword);
            } else if (messageText.equals("/settings")) {
                sendTextMessage(update.getMessage().getChatId().toString(), "Coming soon...");
            }
        }
    }

    private String generatePassword(int length) {
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()";
        String allChars = uppercase + lowercase + numbers + specialChars;

        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allChars.length());
            password.append(allChars.charAt(index));
        }

        return password.toString();
    }

    private void sendTextMessage(String chatId, String text) {
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


