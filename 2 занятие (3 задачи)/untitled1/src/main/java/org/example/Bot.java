package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long id = update.getMessage().getChatId();
            if (messageText.equals("/cat")) {
                String FactCat = getRandomCatFact();
                sendMessage(id, FactCat);
            } else if (messageText.equals("/dog")) {
                String FactDog = getRandomDogFact();
                sendMessage(id, FactDog);
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        try {
            execute(new SendMessage(String.valueOf(chatId), text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getRandomCatFact() {
        String[] FactsCat = {
                "Кошки дальнозорки. Они хорошо видят объекты вдалеке, но небольшие предметы вблизи они видят нечетко.",
                "В Англии кошек привлекают для охраны пищевых складов и зернохранилищ. Одна кошка, охотящаяся на мышей, за год способна спасти до 10 тонн зерна.",
                "Домашние кошки с помощью мяуканья друг с другом не общаются. Эти сигналы они используются только для общения с людьми.",
                "В скелете кошки почти на 40 костей больше, чем в скелете человека.",
                "Кошки плохо различают вкус еды. Кроме того, они не имеют вкусовых рецепторов для сладкого.",
                "Рисунок кожи на носу у кошки такой же уникальный, как и отпечатки пальцев у человека.",
                "Кошки спят от 16 до 20 часов в день. При этом треть времени, что они не спят, — тратят на уход за собой.",
                "Кошки не потеют всем телом, как люди. Пот у них выделяют только подушечки на лапках.",
                "Кошки — плотоядные. Поэтому берут энергию только из белков и жиров, а углеводы для них бесполезны."
        };
        return FactsCat[new Random().nextInt(FactsCat.length)];
    }

    private String getRandomDogFact() {
        String[] FactsDog = {
                "Собаки не испытывают чувство вины.",
                "Собаки умеют заводить друзей.",
                "Самая древняя порода собак в мире - салюки."
        };
        return FactsDog[new Random().nextInt(FactsDog.length)];
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

