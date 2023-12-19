package org.habittracker.command;

import lombok.SneakyThrows;
import org.habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.*;

public class MyHabits implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String MYHABIT = "Список ваших привычек";
    public MyHabits(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    ///////////////////////////////////
    @SneakyThrows
    public void myHabits(long chatId) {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/habits_tracker", "username", "password");
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT name FROM habits WHERE chat_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, chatId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Мои привычки:");
                do {
                    String habitName = rs.getString("name");
                    System.out.println(habitName);
                } while (rs.next());
            } else {
                System.out.println("Вы еще не добавили ни одной привычки.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MYHABIT);
    }

}



