package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;

public class MyAmazingBot extends TelegramLongPollingBot {

    private HashMap<String, String> citaty;

    public MyAmazingBot() {
        citaty = new HashMap<>();
        citaty.put("Наполеон Хилл", "Что разум человека может постигнуть и во что он может поверить, того он способен достичь");
        citaty.put("Амелия Эрхарт", "Сложнее всего начать действовать, все остальное зависит только от упорства");
        citaty.put("Федор Достоевский", "Надо любить жизнь больше, чем смысл жизни");
        citaty.put("Сократ", "Неосмысленная жизнь не стоит того, чтобы жить");
        citaty.put("Аристотель", "Есть только один способ избежать критики: ничего не делайте, ничего не говорите и будьте никем");
        citaty.put("Альберт Эйнштейн", "Есть два способа жить: вы можете жить так, как будто чудес не бывает, и вы можете жить так, как будто всё в этом мире является чудом");
        citaty.put("Авраам Линкольн", "Люди обычно настолько счастливы, насколько решают");

    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (citaty.containsKey(messageText)) {
                send(update.getMessage().getChatId(), citaty.get(messageText));
            } else {
                send(update.getMessage().getChatId(), "Для этой личности нет цитаты в боте");
            }
        }

    }
    private void send(Long chatId, String citaty) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(citaty);
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
