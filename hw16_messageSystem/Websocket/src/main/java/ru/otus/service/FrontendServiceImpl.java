package ru.otus.service;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.UserSystemMessage;

import static ru.otus.MessageSystemConfig.DATABASE_SERVICE_CLIENT_NAME;
import static ru.otus.MessageSystemConfig.FRONTEND_SERVICE_CLIENT_NAME;


public class FrontendServiceImpl implements FrontendService {

    private final MessageSystem messageSystem;

    public FrontendServiceImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void saveUser(UserSystemMessage userData, MessageCallback<UserSystemMessage> dataConsumer) {
        MsClient frontendClient = messageSystem.getClient(FRONTEND_SERVICE_CLIENT_NAME);
        Message outMsg = frontendClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, userData,
                MessageType.USER_DATA, dataConsumer);
        frontendClient.sendMessage(outMsg);
    }
}
