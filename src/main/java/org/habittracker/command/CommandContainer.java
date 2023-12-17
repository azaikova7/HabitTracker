package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;

import java.util.HashMap;

import static org.habittracker.command.CommandName.*;


public class CommandContainer {
    private final HashMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandMap.put(HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        commandMap.put(ADDHABIT.getCommandName(), new AddHabit(sendBotMessageService));
        commandMap.put(MYHABITS.getCommandName(), new MyHabits(sendBotMessageService));
        commandMap.put(PROGRESS.getCommandName(), new Progress(sendBotMessageService));
        commandMap.put(NO.getCommandName(), new NoCommand(sendBotMessageService));
        unknownCommand = new UnknowCommand(sendBotMessageService);
    }
    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
