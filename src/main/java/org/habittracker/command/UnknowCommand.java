package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknowCommand implements Command{
    public static final String UNKNOWN_MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите /help чтобы узнать, что я понимаю.";

    private final SendBotMessageService sendBotMessageService;

    public UnknowCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), UNKNOWN_MESSAGE);
    }
}
