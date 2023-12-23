package org.habittracker.command;

import org.habittracker.service.DataBaseConnection;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import org.habittracker.service.SendCallBack;

public class DeleteHabit {
    public void execute(Update update, TelegramBot bot) {
        DataBaseConnection db = new DataBaseConnection();
        List<String> taskNames = db.nameTaskCreator(update.getMessage().getFrom().getId());
        ArrayList<String> task = new ArrayList<String>();
        for (String i : taskNames){
            task.add("DELETE " + i);
        }
        SendCallBack sendCallback = new SendCallBack();
        sendCallback.execute(update, bot, task, "Выберите задачу");
    }
}

