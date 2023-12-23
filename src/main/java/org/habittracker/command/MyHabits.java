package org.habittracker.command;

import org.habittracker.service.DataBaseConnection;
import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

public class MyHabits implements Command {
    private final SendBotMessageService sendBotMessageService;
    public MyHabits(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    @Override
    public void execute(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        DataBaseConnection db = new DataBaseConnection();
        List<String> habits = db.getHabitsByUserId(update.getMessage().getFrom().getId());
        if (habits.isEmpty()) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId(), "У вас еще нет привычек.");
        } else {
            StringBuilder message = new StringBuilder("Ваши привычки:\n");
            for (String habit : habits) {
                message.append("- ").append(habit).append("\n");
            }
            sendBotMessageService.sendMessage(userId, message.toString());
        }
    }
}
