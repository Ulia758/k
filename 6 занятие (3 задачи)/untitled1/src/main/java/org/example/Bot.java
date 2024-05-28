package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends TelegramLongPollingBot {

    private static final String DirectoryMem = "E:\\Моё\\Курсы Java Колосова\\4 модуль\\6 занятие\\untitled1\\Мемы";
    private static final String StudyMem = "E:\\Моё\\Курсы Java Колосова\\4 модуль\\6 занятие\\untitled1\\Мемы\\Учёба";
    private static final String LifeMem = "E:\\Моё\\Курсы Java Колосова\\4 модуль\\6 занятие\\untitled1\\Мемы\\Жизнь";

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {
                sendCateg(chatId);
            }
        }
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            String callbackQueryId = update.getCallbackQuery().getId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String memesDirectoryPath = "";
            if ("Мемы про учёбу".equals(data)) {
                memesDirectoryPath = StudyMem;
            } else if ("Мемы про жизнь".equals(data)) {
                memesDirectoryPath = LifeMem;
            }
            File memesFolder = new File(memesDirectoryPath);
            File[] memes = memesFolder.listFiles();

            if (memes != null && memes.length > 0) {
                File randomMeme = memes[new Random().nextInt(memes.length)];
                sendPhoto(chatId, randomMeme);
            }
            AnswerCallbackQuery close = new AnswerCallbackQuery();
            close.setCallbackQueryId(callbackQueryId);
            try {
                execute(close);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void sendPhoto(long chatId, File photo) {
        SendPhoto send = new SendPhoto();
        send.setChatId(chatId);
        send.setPhoto(new InputFile(photo));
        try {
            execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCateg(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите категорию мемов: ");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        File mem = new File(DirectoryMem);
        if (mem.exists() && mem.isDirectory()) {
            for (File category : mem.listFiles()) {
                if (category.isDirectory()) {
                    row.add(new InlineKeyboardButton().builder().text(category.getName()).callbackData(category.getName()).build());
                }
            }
        }
        rows.add(row);
        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);
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