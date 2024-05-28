package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    private final FinanceService financeService = new FinanceService();
    private String mode = "none";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            if (text.equals("/start")) {
                sendReplyMessage(message, "Привет! Я бот для учета личных финансов. Воспользуйтесь командами /add (для добавления операции) или /get (для получения записей на дату).");
            } else if (text.startsWith("/add")) {
                mode = "add";
                sendReplyMessage(message, "Пожалуйста, отправьте данные о расходах или доходах в формате: Дата (15.09.2015), Сумма (300), Тип (Доход/Расход), Категория (Продукты)");
            } else if (text.startsWith("/get")) {
                mode = "get";
                sendReplyMessage(message, "Пожалуйста, отправьте дату, для которой вы хотите получить записи в формате");
            } else if (mode.equals("add")) {
                mode = "none";
                processAdd(message);
            } else if (mode.equals("get")) {
                mode = "none";
                processGet(message);
            }  else {
                sendReplyMessage(message, "Я не понял ваш запрос. Воспользуйтесь командами /add (для добавления операции) или /get (для получения записей на дату).");
            }
        }
    }

    private void processGet(Message message) {
        List<FinanceRecord> recordsList = financeService.getFinanceRecordsByDate(message.getText(), message.getChatId());
        if (recordsList.isEmpty()) {
            sendReplyMessage(message, "Для данной даты операций нет");
        } else {
            StringBuilder response = new StringBuilder();
            for (FinanceRecord record : recordsList) {
                response.append("------------------\n");
                response.append("Дата: ").append(record.getDateOfOperation()).append("\n");
                response.append("Сумма: ").append(record.getSumOfOperation()).append("\n");
                response.append("Тип: ").append(record.getTypeOfOperation()).append("\n");
                response.append("Категория: ").append(record.getCategoryOfOperation()).append("\n");
                response.append("------------------" + "\n");
            }
            sendReplyMessage(message, response.toString());
        }
    }

    private void processAdd(Message message) {
        String[] parts = message.getText().split(", ");
        System.out.println(parts[0]);
        financeService.addRecordToUser(message.getChatId(), parts[0], parts[1], parts[2], parts[3]);
        sendReplyMessage(message, "Запись добавлена!");
    }


    private void sendReplyMessage(Message message, String text) {
        try {
            execute(new SendMessage(message.getChatId().toString(), text));
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
