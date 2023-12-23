package org.habittracker.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.habittracker.command.CommandContainer;
import org.habittracker.model.User;
import org.habittracker.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import java.lang.String;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Configuration
@Data
@PropertySource("application.properties")
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
    private HashMap<String, String> habit;

    private final DataBaseConnection db;
    private final SendBotMessageServiceImpl sendBotMessageServiceImpl;

    public TelegramBot() {
        this.sendBotMessageServiceImpl = new SendBotMessageServiceImpl(this);
        this.habit = new HashMap<>();
        this.db = new DataBaseConnection();
        SetMyCommands();
    }
    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    private CommandContainer commandContainer;
    public void SetMyCommands() {
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Начало работы"));
        listofCommands.add((new BotCommand("/help", "Как пользоваться ботом")));
        listofCommands.add(new BotCommand("/myhabits", "Мои привычки"));
        listofCommands.add(new BotCommand("/addhabit", "Добавить привычку"));
        commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            db.addUser(update);
            Long userId = update.getMessage().getFrom().getId();
            User user = db.getUserById(userId);
            if (user.getUserState().equals("START")) {
                CommandContainer command = new CommandContainer(sendBotMessageServiceImpl);
                command.processCommand(update, this);
            }else if (user.getUserState().equals("HABIT")) {
                db.addHabit(update, habit.get("NAME"), update.getMessage().getText());
                habit = new HashMap<>();
                db.editState(userId, "START");
                sendBotMessageServiceImpl.sendMessage(user.getUserId(), "Привычка успешно добавлена");
            }
        }
    }
}
