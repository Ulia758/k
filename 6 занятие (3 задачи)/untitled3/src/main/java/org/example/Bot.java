package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;

public class Bot extends TelegramLongPollingBot {
    private Set<String> usedCities = new HashSet<>();
    private Set<String> cities = new HashSet<>();
    private boolean gameStarted = false;
    String lastCity;
    public Bot() {
        cities.add("астрахань");
        cities.add("санкт-Петербург");
        cities.add("новосибирск");
        cities.add("екатеринбург");
        cities.add("нижний Новгород");
        cities.add("казань");
        cities.add("челябинск");
        cities.add("омск");
        cities.add("самара");
        cities.add("ростов-на-Дону");
        cities.add("уфа");
        cities.add("красноярск");
        cities.add("пермь");
        cities.add("воронеж");
        cities.add("волгоград");
        cities.add("краснодар");
        cities.add("саратов");
        cities.add("тюмень");
        cities.add("тольятти");
        cities.add("ижевск");
        cities.add("дмитров");
        cities.add("магадан");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                sendMessage(chatId, "Добро пожаловать в игру \"Города\"! Правила игры: напиши город, и я назову город на последнюю букву этого слова. Для начала игры отправь команду /game!");
            }
            else if ("/game".equals(messageText)) {
                gameStarted = true;
                sendMessage(chatId, "Я начинаю. Москва, тебе на букву А");
                usedCities.add("москва");
            } else {
                handleCityInput(chatId, messageText);
            }
        }
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void handleCityInput(Long chatId, String userCity) {
        String lastCity = userCity.trim().toLowerCase();
        String response;
        String responseCity = "none";
        if (usedCities.contains(lastCity)) {
            response = "Город " + userCity + " уже был назван. Назовите другой город.";
        } else {
            usedCities.add(lastCity);
            char lastChar = lastCity.charAt(lastCity.length() - 1);
            for (String city: cities) {
                if (city.startsWith(String.valueOf(lastChar))) {
                    responseCity = city;
                }
            }
            usedCities.add(responseCity);
            cities.remove(responseCity);
            if ("none".equals(responseCity)) {
                response = "Я не знаю больше городов на " + lastChar + ". Ты выиграл!";
            } else {
                responseCity = responseCity.toUpperCase();
                response = "Мой город на букву \"" + lastChar + "\" - " + responseCity + ". Твой ход!";
            }
        }
        sendMessage(chatId, response);
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
