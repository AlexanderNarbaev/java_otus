package ru.otus.messagesystem.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagesystem.model.UserSystemMessage;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;

import java.util.Optional;

@Controller
public class GetUserDataRequestHandler implements RequestHandler<UserSystemMessage> {
    @Autowired
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public GetUserDataRequestHandler(UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub) {
        this.userServiceBlockingStub = userServiceBlockingStub;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserSystemMessage userSystemMessage = MessageHelper.getPayload(msg);
        userSystemMessage.setUser(userServiceBlockingStub.saveUser(userSystemMessage.getUser()));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, userSystemMessage));
    }
}
