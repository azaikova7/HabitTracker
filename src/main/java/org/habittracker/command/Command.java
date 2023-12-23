package org.habittracker.command;

import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update, TelegramBot bot);

    void execute(Update update);
}
