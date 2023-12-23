package org.habittracker.service;

import org.springframework.stereotype.Service;

@Service
public interface SendBotMessageService {
    void sendMessage(Long chatId, String message);

}
