package ru.otus.service;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.model.UserSystemMessage;

public interface FrontendService {
    void saveUser(UserSystemMessage userData, MessageCallback<UserSystemMessage> dataConsumer);
}

