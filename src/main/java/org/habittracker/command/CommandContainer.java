package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.habittracker.command.CommandName.*;

import java.util.HashMap;


public class CommandContainer {
    private HashMap<String, Command> commandMap;
    private Command unknownCommand;
    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandMap.put(HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        commandMap.put(ADDHABIT.getCommandName(), new AddHabit(sendBotMessageService));
        commandMap.put(MYHABITS.getCommandName(), new MyHabits(sendBotMessageService));
        /*commandMap.put(DELETEHABIT.getCommandName(), new DeleteHabit(bot));*/
        commandMap.put(NO.getCommandName(), new NoCommand(sendBotMessageService));
        unknownCommand = new UnknowCommand(sendBotMessageService);
    }

    public void processCommand(Update update, TelegramBot bot) {
        String command = update.getMessage().getText();
        Command botCommand = commandMap.get(command);
        if (botCommand != null) {
            botCommand.execute(update);
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