package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.*;
import java.util.ArrayList;

public class TrainerBot extends TelegramLongPollingBot {

    private static final String FILE_NAME = "training_data.txt";
    private ArrayList<String> trainingData = new ArrayList<>();
    private int currentUserId = 0;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            String text = message.getText();

            if (text.equals("/start")) {
                currentUserId = message.getFrom().getId();
                sendTrainingDataList(message.getChatId());
            } else if (currentUserId == message.getFrom().getId()) {
                trainingData.add(text);
                saveTrainingDataToFile();
                sendTrainingDataList(message.getChatId());
            }
        }
    }

    public void sendTrainingDataList(Long chatId) {
        StringBuilder trainingList = new StringBuilder();
        for (int i = 0; i < trainingData.size(); i++) {
            trainingList.append(i).append(". ").append(trainingData.get(i)).append("\n");
        }

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(trainingList.toString());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void saveTrainingDataToFile() {
        try {
            PrintWriter writer = new PrintWriter(FILE_NAME);
            for (String data : trainingData) {
                writer.println(data);
            }
            writer.close();
        } catch (FileNotFoundException e) {
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
