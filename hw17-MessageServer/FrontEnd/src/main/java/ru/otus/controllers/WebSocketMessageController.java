package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.domain.NewUserMessage;
import ru.otus.domain.WebSocketMessage;
import ru.otus.model.UserFront;
import ru.otus.service.FrontendService;

import java.util.Collections;

@Controller
public class WebSocketMessageController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageController.class);


    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public WebSocketMessageController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.template = template;
        this.frontendService = frontendService;
    }

    @MessageMapping("/getAllUsers")
    @SendTo("/topic/usersList")
    public WebSocketMessage sendMessage() {
        logger.info("got getAllUsers");
        return new WebSocketMessage(frontendService.getUsers().orElse(Collections.emptyList()));
    }

    @MessageMapping("/newUserMessage")
    @SendTo("/topic/userCreateStarted")
    public void getMessage(NewUserMessage newUserMessage) {
        logger.info("got newUserMessage:{}", newUserMessage);
        frontendService.saveUser(new UserFront(0L, newUserMessage.getName(), newUserMessage.getLogin(), newUserMessage.getName()));
        template.convertAndSend("/topic/usersList", sendMessage());
    }
}
