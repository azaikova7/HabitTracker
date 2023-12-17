package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.Message;

import telegrambot.DatabaseManager;
import telegrambot.TelegramBot;
import telegrambot.models.Habit;

import java.util.List;

public class MyHabits implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String MYHABIT = "Список ваших привычек";
    public MyHabits(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    private final DatabaseManager databaseManager;

    public MyHabitsCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(Message message, TelegramBot bot) {
        long chatId = message.getChatId();
        long userId = message.getFrom().getId();

        List<Habit> userHabits = databaseManager.getUserHabits(userId);

        if (userHabits.isEmpty()) {
            bot.sendTextResponse(chatId, "привычек еще нет.");
        } else {
            String response = "привычки:\n\n";
            for (int i = 0; i < userHabits.size(); i++) {
                Habit habit = userHabits.get(i);
                response += (i + 1) + ". " + habit.getName() + "\n";
            }
            bot.sendTextResponse(chatId, response);
        }
    }

    @Override
    public String getDescription() {
        return "/myhabits - get a list of your habits";
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MYHABIT);
    }

}



