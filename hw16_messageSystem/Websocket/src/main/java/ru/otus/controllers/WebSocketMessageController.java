package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.domain.NewUserMessage;
import ru.otus.domain.WebSocketMessage;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.model.User;
import ru.otus.model.UserSystemMessage;
import ru.otus.service.FrontendService;
import ru.otus.services.DBServiceUser;

@Controller
public class WebSocketMessageController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageController.class);


    private final SimpMessagingTemplate template;
    private final DBServiceUser usersService;
    private final FrontendService frontendService;
    private final MessageSystem messageSystem;

    public WebSocketMessageController(SimpMessagingTemplate template, DBServiceUser usersService, FrontendService frontendService, MessageSystem messageSystem) {
        this.template = template;
        this.usersService = usersService;
        this.frontendService = frontendService;
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
        frontendService.saveUser(new UserSystemMessage(new User(0L, newUserMessage.getName(), newUserMessage.getLogin(), newUserMessage.getPassword())),
                t -> this.template.convertAndSend("/topic/usersList", this.sendMessage()));
    }
}
