package org.habittracker.command;

import lombok.SneakyThrows;
import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;

public class AddHabit implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String ADD = "Введите свою привычку";
    public AddHabit(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    ///////////////////////////////////////////////////

    @SneakyThrows
    public void AddHabit(String habitName, long chatId) {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/habitbot", "tgbot", "tg-bot-123");
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO habits (name, chat_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, habitName);
            stmt.setLong(2, chatId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Привычка успешно добавлена.");
            } else {
                System.out.println("Ошибка.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ADD);
    }
}
