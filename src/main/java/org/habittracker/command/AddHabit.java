package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AddHabit implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String ADD = "Введите свою привычку";
    public AddHabit(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ADD);
    }
}
