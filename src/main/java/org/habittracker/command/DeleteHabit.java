package org.habittracker.command;

import org.habittracker.service.DataBaseConnection;
import org.habittracker.service.SendBotMessageServiceImpl;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class DeleteHabit {
    private final TelegramBot telegramBot;

    public DeleteHabit(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    public void execute(Update update) {
        DataBaseConnection db = new DataBaseConnection();
        List<String> taskNames = db.nameTaskCreator(update.getMessage().getFrom().getId());
        ArrayList<String> task = new ArrayList<String>();
        for (String i : taskNames){
            task.add("DELETE " + i);
        }
        SendBotMessageServiceImpl sendCallback = new SendBotMessageServiceImpl(telegramBot);
        sendCallback.execute(update, task, "Выберите задачу");
    }
}

