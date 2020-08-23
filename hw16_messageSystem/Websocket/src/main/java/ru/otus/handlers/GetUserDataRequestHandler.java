package ru.otus.handlers;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler<User> {
    private final DBServiceUser dbService;

    public GetUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User userData = MessageHelper.getPayload(msg);
        userData.setId(dbService.saveUser(userData));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, userData));
    }
}
