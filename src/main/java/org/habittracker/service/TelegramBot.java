package org.habittracker.service;

import lombok.extern.slf4j.Slf4j;
import org.habittracker.command.CommandContainer;
import org.habittracker.config.BotConfig;
import org.habittracker.model.User;
import org.habittracker.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.habittracker.command.CommandName.NO;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;
    private BotConfig config;

    private HashMap<String, String> habit;
    public static String COMMAND_PREFIX = "/";
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;

    public DataBaseConnection db;
    final String botToken;
    public TelegramBot(BotConfig config, String botToken) {

        this.config = config;
        this.botToken = botToken;
        this.habit = new HashMap<>();
        this.db = new DataBaseConnection();
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Начало работы"));
        listofCommands.add((new BotCommand("/help", "Как пользоваться ботом")));
        listofCommands.add(new BotCommand("/myhabits", "Мои привычки"));
        listofCommands.add(new BotCommand("/addhabit", "Добавить привычку"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private CommandContainer commandContainer;

    public TelegramBot(String botToken) {
        this.botToken = botToken;
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), this);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        registerUser(update.getMessage());
        if (update.hasMessage() && update.getMessage().hasText()) {
            registerUser(update.getMessage());
            if (update.hasMessage() && update.getMessage().hasText()) {
                String message = update.getMessage().getText().trim();
                if (message.startsWith(COMMAND_PREFIX)) {
                    String commandIdentifier = message.split(" ")[0].toLowerCase();

                    commandContainer.retrieveCommand(commandIdentifier).execute(update);
                } else {

                    commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
                }
            }
        }
        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            Long userId = callbackQuery.getFrom().getId();
            if (data.startsWith("TASK")){
                habit.put("TASK", data);
                ArrayList<String> action = new ArrayList<>(Arrays.asList("ACTION дедлайн", "ACTION описание", "ACTION статус", "ACTION выполняющий"));
                SendBotMessageServiceImpl send = new SendBotMessageServiceImpl(this);
                send.execute(update, action, "выберите действие");
            }
            if (data.startsWith("ACTION")){
                habit.put("ACTION", data);
                db.editState(userId, "EDITTASK");
                sendMessage("Введите новые данные", callbackQuery.getMessage().getChatId().toString());
            }
            /*if (data.startsWith("MYTASK")){
                String message = db.infoTask(userId, data.split(" ")[1]);
                sendMessage(message, callbackQuery.getMessage().getChatId().toString());
            }
            if (data.startsWith("DELETE")){
                System.out.println(data.split(" ")[1]);
                db.deleteTaskByTaskName(data.split(" ")[1]);
                sendMessage("Задача успешно удалена", callbackQuery.getMessage().getChatId().toString());
            }*/
        }
    }


    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
}
