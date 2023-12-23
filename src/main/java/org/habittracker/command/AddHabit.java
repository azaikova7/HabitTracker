package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.TelegramBot;
import org.habittracker.service.DataBaseConnection;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.habittracker.model.User;




public class AddHabit implements Command {

    private final SendBotMessageService sendBotMessageService;

    public AddHabit(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    public void execute(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        DataBaseConnection db = new DataBaseConnection();
        User user = db.getUserById(userId);
        db.editState(userId, "NAME");
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), "Введите привычку");
    }

}

