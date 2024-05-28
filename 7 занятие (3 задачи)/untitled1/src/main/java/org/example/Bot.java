package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            String messageText = message.getText();
            long chatId = message.getChatId();

            if (messageText.equals("/start")) {
                sendTextMessage(chatId, "Я бот для скачивания png изображений. Тебе нужно только отправить мне ссылку!");
            } else if (messageText.startsWith("https://") && messageText.endsWith(".png")) {
                File photoFile = downloadImage(messageText);
                sendImage(chatId, photoFile);
            } else {
                sendTextMessage(chatId, "Неверный формат ссылки! Ссылка должна иметь формат https://1c.ru/static/images/logo.png");
            }

        }
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

    private File downloadImage(String imageURL) {

        Path path = Path.of("image.png");
        try {
            URL url = new URL(imageURL);
            InputStream input = url.openStream();
            Files.copy(input, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path.toFile();
    }

    private void sendImage(long chatId, File photo) {
        try {
            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(photo))
                    .build();
            execute(sendPhoto);
            Files.deleteIfExists(photo.toPath());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
