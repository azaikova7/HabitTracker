package org.habittracker.command;

import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.*;

public class AddHabit implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String ADD = "Введите свою привычку";
    public AddHabit(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    private Connection conn;

    public void AddHabitCommand(Connection conn){
        this.conn = conn;
    }

    public void addHabit(int userId, String habitName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO habits (user_id, name) VALUES (?, ?)");
        stmt.setInt(1, userId);
        stmt.setString(2, habitName);
        stmt.executeUpdate();
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ADD);
    }
}
