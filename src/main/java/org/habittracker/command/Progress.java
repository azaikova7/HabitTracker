package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.List;

public class Progress implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String PROGRESS = "Ваш прогресс";
    public Progress(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


//////////////////////////////////////////////////////////ничего твоего не стирала


    public class MyProgressCommand implements Command {
        private HabitTracker habitTracker;
        public MyProgressCommand(HabitTracker habitTracker) {
            this.habitTracker = habitTracker;
        }

        @Override
        public String execute(Message message) {
            long chatId = message.getChatId();

            List<Habit> habits = habitTracker.getHabitsForUser(chatId);
            String progress = generateProgressTable(habits);

            return progress;
        }

        private String generateProgressTable(List<Habit> habits) {
            StringBuilder table = new StringBuilder();
            table.append("Habit Name | Progress\n");
            for (Habit habit : habits) {
                table.append(habit.getName()).append(" | ").append(calculateProgress(habit)).append("%\n");
            }
            return table.toString();
        }

        private int calculateProgress(Habit habit) {
            // тут надо написать принцип как будем считать баллы
            return habit.getProgress();
        }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), PROGRESS);
    }
}

