package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            if (text.startsWith("/start")) {
                sendReply(message, "Привет. Это бот для расчёта калорий. Введите свой пол, возраст, вес, рост, физическую нагрузку (отсутствие, легкая, умеренная, высокая) и цель (похудение, набор, поддержание) без запятых в строчку");
            } else {
                String[] userData = update.getMessage().getText().split(" ");
                if (userData.length != 6) {
                    sendReply(message, "Неверный формат данных");
                } else {
                    String pol = userData[0];
                    int age = Integer.parseInt(userData[1]);
                    double weight = Double.parseDouble(userData[2]);
                    double height = Double.parseDouble(userData[3]);
                    String activn = userData[4];
                    String cel = userData[5];
                    int ccal = vichislenie(pol, age, weight, height, activn, cel);
                    String res = "Норма калорий для вашей цели: " + ccal;
                    sendReply(message, res);
                }
            }
        }
    }
    private int vichislenie(String pol, int age, double weight, double height, String activn, String cel) {
        int bmr;
        if (pol.equalsIgnoreCase("женский")) {
            bmr = (int) (655 + (4.35 * weight) + (4.7 * height) - (4.7 * age));
        } else {
            bmr = (int) (66 + (6.23 * weight) + (15.5 * height) - (6.8 * age));
        }
        double activnPlus;
        switch (activn) {
            case "отсутствие":
                activnPlus = 1.2;
                break;
            case "легкая":
                activnPlus = 1.375;
                break;
            case "умеренная":
                activnPlus = 1.55;
                break;
            case "высокая":
                activnPlus = 1.725;
                break;
            default:
                activnPlus = 1.2;
        }
        int tdee = (int) (bmr * activnPlus);
        int dailyCalories;
        switch (cel) {
            case "похудение":
                dailyCalories = tdee - 500;
                break;
            case "набор":
                dailyCalories = tdee + 500;
                break;
            case "поддержание":
                dailyCalories = tdee;
                break;
            default:
                dailyCalories = tdee;
        }
        return dailyCalories;
    }
    private void sendReply(Message message, String quote) {
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId());
        reply.setText(quote);
        try {
            execute(reply);
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
