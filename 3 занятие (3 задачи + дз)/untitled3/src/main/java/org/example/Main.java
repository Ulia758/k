package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

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

    private Map<Long, Integer> userProgress = new HashMap<>();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {
                userProgress.put(chatId, 1);
                textMessage(chatId, "Ты находишься на кладбище, в какую сторону пойдёшь?", "Направо", "Налево");
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String chose = update.getCallbackQuery().getData();
            int currentProgress = userProgress.get(chatId);
            switch (currentProgress) {
                case 1:
                    if (chose.equals("Направо")) {
                        textMessage(chatId, "Перед тобой развилка, куда повернёшь?", "Налево", "Направо");
                        userProgress.put(chatId, 2);
                    } else {
                        sendTextMessage(chatId, "Ты встретил привидение, которое тебя убило. Игра окончена");
                        userProgress.remove(chatId);
                    }
                    break;
                case 2:
                    if (chose.equals("Налево")) {
                        sendTextMessage(chatId, "Ты нашел сокровища и победил! Игра окончена.");
                        userProgress.remove(chatId);
                    } else {
                        sendTextMessage(chatId, "Ты упал в яму. Игра окончена.");
                        userProgress.remove(chatId);
                    }
                    break;
            }
            sendCallbackQueryAnswer(update.getCallbackQuery().getId());
        }
    }
    private void textMessage(long chatId, String text, String... options) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (String option : options) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(option)
                    .callbackData(option)
                    .build();
            row.add(button);
            rows.add(row);
        }
        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCallbackQueryAnswer(String callbackQueryId) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery().builder().callbackQueryId(callbackQueryId).build();
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "KolosssssBot";
    }
    @Override
    public String getBotToken() {
        return "6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM";
    }
}

