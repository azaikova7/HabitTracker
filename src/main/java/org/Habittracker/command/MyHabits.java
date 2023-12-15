package org.Habittracker.command;

import org.Habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyHabits implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String MYHABIT = "Список ваших привычек";
    public MyHabits(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MYHABIT);
    }

}
