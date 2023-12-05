package org.Habittracker.config.BotCommand;

import jdk.internal.access.JavaIOFileDescriptorAccess;
import org.Habittracker.model.UserRepository;
import org.Habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class BotCommandHandler {

    private JavaIOFileDescriptorAccess commandMap;

    public BotCommandHandler() {
        HashMap<Object, Object> commandMap = new HashMap<>();
        commandMap.put("/start", new StartCommand());
        commandMap.put("/help", new HelpCommand());
        commandMap.put("/myhabits", new ListOfHabits());
        commandMap.put("/addhabit", new AddHabits());
        commandMap.put("/progress", new MyProgress());

    }
    public void processCommand(Update update, TelegramBot bot, UserRepository userRepository) {
        String userMessage = update.getMessage().getText();
        String[] parts = userMessage.split(" ", 2);
        String command = parts[0];
        BotCommand botCommand = commandMap.get(command);
        if (botCommand != null) {
            botCommand.execute(update, bot, userRepository);
        } else {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Неизвестная команда. Введите /help для получения справки.");
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
