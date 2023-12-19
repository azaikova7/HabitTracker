package org.habittracker.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    MYHABITS("/myhabits"),
    ADDHABIT("/addhabit"),
    NO("");



    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
