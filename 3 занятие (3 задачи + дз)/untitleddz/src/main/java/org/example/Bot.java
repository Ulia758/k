package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;
public class Bot extends TelegramLongPollingBot {

    private void sendInstructions(long chat_id) {
        String instructions = "Добро пожаловать! Этот бот поможет вам изучать иностранный язык. Просто нажмите /start, чтобы начать изучение.";
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(instructions);
        executeMessage(message);
    }
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendWordForTranslation(long chat_id, String foreignWord, List<String> translations) {
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText("Что означает слово: " + foreignWord + "?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String translation : translations) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText(translation).setCallbackData(translation));
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        executeMessage(message);
    }
    private void checkAnswer(Update update, String correctTranslation) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        long chatId = callbackQuery.getMessage().getChatId();
        String userAnswer = callbackQuery.getData().toLowerCase();

        String resultMessage;
        if (userAnswer.equals(correctTranslation.toLowerCase())) {
            resultMessage = "Правильно! Ответ: " + correctTranslation;
        } else {
            resultMessage = "Неправильно. Правильный ответ: " + correctTranslation;
        }

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(resultMessage);
        executeMessage(message);
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            sendInstructions(chat_id);
        } else if (update.hasCallbackQuery()) {
            String word = "apple";
            List<String> translations = new ArrayList<>();
            translations.add("яблоко");
            translations.add("апельсин");
            translations.add("банан");
            sendWordForTranslation(update.getCallbackQuery().getMessage().getChatId(), word, translations);
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

