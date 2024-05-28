package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.Map;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Bot extends TelegramLongPollingBot {

    private Scheduler scheduler;
    private HomeworkManager homeworkManager;
    private UserDataManager dataManager;

    public Bot() {
        this.scheduler = new Scheduler();
        this.homeworkManager = new HomeworkManager();
        this.dataManager = new UserDataManager();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            String messageText = update.getMessage().getText();
            if (messageText.equals("/schedule")) {
                String schedule = scheduler.getSchedule(dataManager.getUserSchedule(chatId));
                sendMessage(chatId, schedule);
            } else if (messageText.equals("/homework")) {
                String homework = homeworkManager.getHomework(dataManager.getUserHomework(chatId));
                sendMessage(chatId, homework);
            } else if (messageText.startsWith("/addSchedule")) {
                String[] tokens = messageText.split(" ");
                if (tokens.length == 3) {
                    dataManager.addUserSchedule(chatId, tokens[1], tokens[2]);
                    sendMessage(chatId, "Schedule added successfully!");
                }
            } else if (messageText.startsWith("/addHomework")) {
                String[] tokens = messageText.split(" ");
                if (tokens.length >= 3) {
                    String subject = tokens[1];
                    StringBuilder description = new StringBuilder();
                    for (int i = 2; i < tokens.length; i++) {
                        description.append(tokens[i]).append(" ");
                    }
                    dataManager.addUserHomework(chatId, subject, description.toString());
                    sendMessage(chatId, "Домашняя работа успешно добавлена!");
                }
            } else {
                sendMessage(chatId, "Неизвестная команда");
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        try {
            execute(MessageHelper.createMessage(chatId, text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public class Scheduler {
        public String getSchedule(Map<String, String> scheduleData) {
            StringBuilder schedule = new StringBuilder("Твоё расписание:\n");
            for (Map.Entry<String, String> entry : scheduleData.entrySet()) {
                schedule.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return schedule.toString();
        }
    }

    public class HomeworkManager {
        public String getHomework(Map<String, String> homeworkData) {
            StringBuilder homework = new StringBuilder("Твоя домашняя работа:\n");
            for (Map.Entry<String, String> entry : homeworkData.entrySet()) {
                homework.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return homework.toString();
        }
    }

    public class UserDataManager {

        private Map<Long, Map<String, String>> userSchedules;
        private Map<Long, Map<String, String>> userHomeworks;

        public UserDataManager() {
            this.userSchedules = new HashMap<>();
            this.userHomeworks = new HashMap<>();
        }

        public Map<String, String> getUserSchedule(long chatId) {
            return userSchedules.getOrDefault(chatId, new HashMap<>());
        }

        public Map<String, String> getUserHomework(long chatId) {
            return userHomeworks.getOrDefault(chatId, new HashMap<>());
        }

        public void addUserSchedule(long chatId, String day, String lessons) {
            Map<String, String> userSchedule = userSchedules.getOrDefault(chatId, new HashMap<>());
            userSchedule.put(day, lessons);
            userSchedules.put(chatId, userSchedule);
        }

        public void addUserHomework(long chatId, String subject, String description) {
            Map<String, String> userHomework = userHomeworks.getOrDefault(chatId, new HashMap<>());
            userHomework.put(subject, description);
            userHomeworks.put(chatId, userHomework);
        }
    }
    public class MessageHelper {

        public SendMessage createMessage(long chatId, String text) {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText(text);
        }

        public SendMessage createReplyMessage(Message message, String text) {
            return new SendMessage()
                    .setChatId(message.getChatId())
                    .setReplyToMessageId(message.getMessageId())
                    .setText(text);
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
