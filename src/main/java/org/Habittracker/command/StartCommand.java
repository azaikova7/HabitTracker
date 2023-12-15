package org.Habittracker.command;

import org.Habittracker.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    public final static String START_MESSAGE = "Привет, я - habit tracker - я помогу тебе ослеживать и корректировать свои привычки - полезные и вредные\n"+
            "Давай создадим твою первую привычку, чтобы приобрести или избавиться от неё\n\n"+
            "Напиши мне, что ты хочешь выполнять, например:\n\n"+
            "- Читать 10 страниц в день;\n\n"+
            "- Бросить курить";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
