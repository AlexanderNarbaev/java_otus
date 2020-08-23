package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.domain.NewUserMessage;
import ru.otus.domain.WebSocketMessage;
import ru.otus.handlers.GetUserDataRequestHandler;
import ru.otus.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.*;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

@Controller
public class WebSocketMessageController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageController.class);
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    private final DBServiceUser usersService;

    MessageSystem messageSystem = new MessageSystemImpl();
    CallbackRegistry callbackRegistry = new CallbackRegistryImpl();
    HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
    MsClient databaseMsClient;
    MsClient frontendMsClient;

    public WebSocketMessageController(DBServiceUser usersService) {
        this.usersService = usersService;
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(this.usersService));
        databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

        frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);
        MessageCallback<User> dataConsumer = new MessageCallback<User>() {
            @Override
            public void accept(User user) {

            }
        };
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
        Message outMsg = frontendMsClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, new User(0L, newUserMessage.getName(), newUserMessage.getLogin(), newUserMessage.getPassword()),
                MessageType.USER_DATA, t -> this.sendMessage());
        frontendMsClient.sendMessage(outMsg);
    }
}
