package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    static final String HELP_TEXT = "Вот такие команды я понимаю:\n\n"+
            "/start - приветствие\n\n" +
            "/help - информация о доступных командах\n\n" +
            "/myhabits - список моих привычек\n\n" +
            "/addhabit - добавить новую привычку\n\n" +
            "/mypoints - информация о баллах, полученных за выполнение привычек\n\n" +
            "/progress - статистика выполнения привычек в виде графика/таблицы\n\n" +
            "/deletedata - удалить всю информацию о пользователе\n\n" +
            "/settings - настройки: например, изменить привычку или время получения уведомлений\n\n";
    public TelegramBot(BotConfig config){

        this.config=config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Начало работы"));
        listofCommands.add((new BotCommand("/help", "Как пользоваться ботом")));
        listofCommands.add(new BotCommand("/myhabits", "Мои привычки"));
        listofCommands.add(new BotCommand("/addhabit", "Добавить привычку"));
        listofCommands.add(new BotCommand("/mypoints", "Мои баллы"));
        listofCommands.add(new BotCommand("/progress", "Прогресс"));
        listofCommands.add(new BotCommand("/deletedata", "Удалить информацию обо мне"));
        listofCommands.add(new BotCommand("/settings", "Настройки"));
        try{
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        }
        catch(TelegramApiException e){
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                default: sendMessage(chatId, "Сори, пока что я не знаю такую команду");
            }
        }

    }
    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name +", я - habit tracker - я помогу тебе ослеживать и корректировать свои привычки - полезные и вредные\n"+
                "Давай создадим твою первую привычку, чтобы приобрести или избавиться от неё\n\n"+
                "Напиши мне, что ты хочешь выполнять, например:\n\n"+
                "- Читать 10 страниц в день;\n\n"+
                "- Бросить курить";
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

    }
}
