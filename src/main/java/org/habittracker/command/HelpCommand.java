package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = """
            Вот такие команды я понимаю:

            /start - приветствие

            /help - информация о доступных командах

            /myhabits - список моих привычек

            /addhabit - добавить новую привычку

            /progress - статистика выполнения привычек в виде графика/таблицы

            """;

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
