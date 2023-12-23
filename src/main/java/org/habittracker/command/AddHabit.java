package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.DataBaseConnection;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class AddHabit implements Command {
    private final SendBotMessageService sendBotMessageService;
    public AddHabit(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    public void execute(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        DataBaseConnection db = new DataBaseConnection();
        db.editState(userId, "HABIT");
        sendBotMessageService.sendMessage(userId, "Введите привычку");
    }
}
