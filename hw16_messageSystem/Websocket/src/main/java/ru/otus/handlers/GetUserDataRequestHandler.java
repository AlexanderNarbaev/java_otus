package ru.otus.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.model.UserSystemMessage;
import ru.otus.services.DBServiceUser;

import java.util.Optional;

@Controller
public class GetUserDataRequestHandler implements RequestHandler<UserSystemMessage> {
    @Autowired
    private final DBServiceUser dbService;

    public GetUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserSystemMessage userSystemMessage = MessageHelper.getPayload(msg);
        userSystemMessage.getUser().setId(dbService.saveUser(userSystemMessage.getUser()));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, userSystemMessage));
    }
}
