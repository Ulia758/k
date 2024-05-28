package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Arrays;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            sendQuestion(update.getMessage().getChatId(), "Какие цвета смешать, чтобы получился зеленый?", "Синий и жёлтый", "Жёлтый и фиолетовый", "Фиолетовый и серый");
            sendQuestion(update.getMessage().getChatId(), "Какая планета самая горячая?", "Венера", "Юпитер", "Сатурн");
            sendQuestion(update.getMessage().getChatId(), "Когда началась Великая Отечественная война?", "В 1941", "В 1939", "В 1940");
        }
    }

    private void sendQuestion(Long chatId, String question, String... options) {
        SendPoll sendPoll = new SendPoll();
        sendPoll.setChatId(chatId.toString());
        sendPoll.setQuestion(question);
        sendPoll.setOptions(Arrays.asList(options));
        sendPoll.setType("quiz");
        sendPoll.setCorrectOptionId(0);
        try {
            execute(sendPoll);
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

