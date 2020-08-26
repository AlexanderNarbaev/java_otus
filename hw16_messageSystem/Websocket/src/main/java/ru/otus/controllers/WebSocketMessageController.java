package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.domain.NewUserMessage;
import ru.otus.domain.WebSocketMessage;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.User;
import ru.otus.model.UserSystemMessage;
import ru.otus.services.DBServiceUser;

import static ru.otus.MessageSystemConfig.DATABASE_SERVICE_CLIENT_NAME;
import static ru.otus.MessageSystemConfig.FRONTEND_SERVICE_CLIENT_NAME;

@Controller
public class WebSocketMessageController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageController.class);

    @Autowired
    private final DBServiceUser usersService;
    @Autowired
    private final MessageSystem messageSystem;

    public WebSocketMessageController(DBServiceUser usersService, MessageSystem messageSystem) {
        this.usersService = usersService;
        this.messageSystem = messageSystem;
    }


    @MessageMapping("/getAllUsers")
    @SendTo("/topic/usersList")
    public WebSocketMessage sendMessage() {
        logger.info("got getAllUsers");
        return new WebSocketMessage(usersService.getUsers().orElse(null));
    }

    @MessageMapping("/newUserMessage")
    @SendTo("/topic/userCreateStarted")
    public void getMessage(NewUserMessage newUserMessage) {
        logger.info("got newUserMessage:{}", newUserMessage);
        MsClient frontendClient = messageSystem.getClient(FRONTEND_SERVICE_CLIENT_NAME);
        Message outMsg = frontendClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, new UserSystemMessage(new User(0L, newUserMessage.getName(), newUserMessage.getLogin(), newUserMessage.getPassword())),
                MessageType.USER_DATA, t -> this.sendMessage());
        frontendClient.sendMessage(outMsg);
    }
}
