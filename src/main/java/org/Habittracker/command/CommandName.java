package org.Habittracker.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    MYHABITS("/myhabits"),
    ADDHABIT("/addhabit"),
    NO(""),
    PROGRESS("/progress");



    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
