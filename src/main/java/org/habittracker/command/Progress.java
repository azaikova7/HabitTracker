package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Progress implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String PROGRESS = "Ваш прогресс";
    public Progress(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), PROGRESS);
    }
}
