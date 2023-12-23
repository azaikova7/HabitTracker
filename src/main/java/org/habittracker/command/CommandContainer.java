package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.habittracker.service.TelegramBot;
/*import org.habittracker.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;*/

import java.util.HashMap;

import static org.habittracker.command.CommandName.*;


public class CommandContainer {
    private final HashMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramBot bot) {
        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandMap.put(HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        commandMap.put(ADDHABIT.getCommandName(), new AddHabit(sendBotMessageService));
        commandMap.put(MYHABITS.getCommandName(), new MyHabits(sendBotMessageService));
        /*commandMap.put(DELETEHABIT.getCommandName(), new DeleteHabit(bot));*/
        commandMap.put(NO.getCommandName(), new NoCommand(sendBotMessageService));
        unknownCommand = new UnknowCommand(sendBotMessageService);


    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}