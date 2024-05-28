package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private Map<String, String> recipes;
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            long chatId = message.getChatId();
            String text = message.getText();
            switch (text) {
                case "/start":
                    sendTextMessage(chatId, "Привет! Я бот рецептов. Чтобы получить рецепт, нажмите на кнопку снизу:");
                    sendRecipeKeyboard(chatId);
                    break;
                case "рецепт смузи":
                    sendTextMessage(chatId, "Рецепт смузи\nИнгредиенты: \n" +
                            "1. Яблоко - 1 шт\n2. Банан - 1 шт\n3. Киви - 1 шт\n4. Мёд - 1 ч.л.\n5. Кефир - 1 стакан (250 гр)" +
                            "\nБанан и киви почистите и порежьте произвольными кусочками.\n" +
                            "Яблоко можно не чистить. Тоже порежьте кусочками.\n" +
                            "В чашу блендера сложите фрукты для зимнего смузи, мед и залейте кефиром.\n" +
                            "Измельчите.\n" +
                            "Простой зимний смузи на кефире готов.");
                    break;
                case "рецепт мороженого":
                    sendTextMessage(chatId, "Рецепт мороженого\nИнгредиенты: \n" +
                            "1. Спелые бананы - 3 шт\n2. Сладкая клубника - 500 гр\n3. Жирное молоко - 100 мл" +
                            "\nБананы очищаем от кожицы, нарезаем небольшими кусочками и отправляем в морозильную камеру на пару часов;\n" +
                            "Аналогично поступаем с клубникой. Ягоду моем, очищаем от плодоножек, нарезаем кубиками и отправляем в морозильную камеру;\n" +
                            "Достаем замороженные бананы и клубнику, блендером взбиваем до однородности. Затем добавляем в массу молоко и еще раз пробиваем. Теперь выкладываем основу в форму для мороженого и отправляем в камеру на 3 часа. Каждые 40 минут достаем мороженое и перемешиваем его, собирая с краев формы, так как масса схватывается там быстрее;\n" +
                            "Как только мороженое застынет, его можно раскладывать по креманкам и подавать к столу.");
                    break;
                case "рецепт салата":
                    sendTextMessage(chatId, "Рецепт салата\nИнгредиенты: \n" +
                            "1. Капуста - 400 гр\n2. Укроп - 10 гр\n3. Зелёный лук - 20 гр\n" +
                            "4. Петрушка - 10 гр\n5. Оливковое масло - 2 ст.л.\n" +
                            "6. Яблочный уксус - 2 ст.л.\n7. Соль - по вкусу\n8. Перец - по вкусу" +
                            "\nКапусту мелко нашинкуйте и положите в глубокую миску. Перья зеленого лука мелко нарежьте. Также мелко нарежьте петрушку. Укроп тоже измельчите. Нарезанную мелко зелень положите в миску к нашинкованной капусте. " +
                            "Всё тщательно перемешайте. Приготовьте заправку для салата. В отдельной ёмкости смешайте соль и перец." +
                            " Добавьте масло и уксус. Все хорошо перемешайте. Оставьте заправку настояться на несколько минут. Полейте салат заправкой и перемешайте." +
                            " По желанию в салат можно добавить свежий огурец. Салат из капусты с уксусом и маслом быстрого приготовления готов.");
                    break;
                case "совет":
                    sendAdvice(chatId);
                    break;
                case "сочетания ингредиентов":
                    sendIngredientCombination(chatId);
                    break;
                default:
                    sendTextMessage(chatId, "Выберите доступный вариант из списка");
                    sendRecipeKeyboard(chatId);
                    break;
            }
        }
    }
    private void sendRecipeKeyboard(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("рецепт смузи");
        row1.add("рецепт мороженого");
        row1.add("рецепт салата");
        row1.add("совет");
        row1.add("сочетания ингредиентов");
        keyboard.add(row1);
        keyboardMarkup.setKeyboard(keyboard);

        org.telegram.telegrambots.meta.api.methods.send.SendMessage message = new org.telegram.telegrambots.meta.api.methods.send.SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите рецепт или полезные советы для приготовления блюд");
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendAdvice(long chatId) {
        String advice = "Чтобы сохранить сочность мяса, не солите его сразу, а только после приготовления";
        sendTextMessage(chatId, advice);
    }

    private void sendIngredientCombination(long chatId) {
        String combination = "Одно из идеальных сочетаний ингредиентов для салата: курица + твёрдый сыр + грибы + лук + розмарин, тмин, винный уксус";
        sendTextMessage(chatId, combination);
    }
    private void sendTextMessage(long chatId, String text) {
        org.telegram.telegrambots.meta.api.methods.send.SendMessage message = new org.telegram.telegrambots.meta.api.methods.send.SendMessage();
        message.setChatId(chatId);
        message.setText(text);
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
