package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputVenueMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasInlineQuery()) {
            AnswerInlineQuery answer = createInlineQueryResult(update);
            try {
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            if (messageText.equals("/start")) {
                sendInstruction(chatId);
            }
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
    public void sendPhoto(String chatId, String photoUrl, String caption) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(photoUrl);
        sendPhotoRequest.setCaption(caption);

        try {
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private AnswerInlineQuery createInlineQueryResult(Update update) {
        String query = update.getInlineQuery().getQuery();
        List<InlineQueryResult> results = new ArrayList<>();
        results.add(new InlineQueryResultArticle("1", "Исторические здания",
                new InputTextMessageContent("Выбрана категория: Исторические здания")));
        results.add(new InlineQueryResultArticle("2", "Парки",
                new InputTextMessageContent("Выбрана категория: Парки")));

        return new AnswerInlineQuery(update.getInlineQuery().getId(),
                List.of(results.toArray(new InlineQueryResult[0])));
    }
    private void sendInstruction(long chatId, String attractionName, String description, String photoUrl) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(photoUrl);
        try {
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(attractionName + "\n\n" + description);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
