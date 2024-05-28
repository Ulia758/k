package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {
                sendStartMessage(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callbackQueryId = update.getCallbackQuery().getId();


            if (callbackData.equals("show_time")) {
                sendTime(chatId, callbackQueryId);
            }
        }
    }

    private void sendTime(long chatId, String callbackQueryId) {
        String currentTime = LocalTime.now().toString().substring(0, 8);
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("Текущее время: " + currentTime);
        AnswerCallbackQuery close = new AnswerCallbackQuery();
        close.setCallbackQueryId(callbackQueryId);

        try {
            execute(response);
            execute(close);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendStartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привет! Я - Времябот. Нажми на кнопку, чтобы узнать время.");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton showTimeButton = new InlineKeyboardButton();
        showTimeButton.setText("Показать время");
        showTimeButton.setCallbackData("show_time");
        row.add(showTimeButton);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
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
