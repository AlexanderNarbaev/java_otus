package ru.otus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.handlers.GetUserDataRequestHandler;
import ru.otus.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.*;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.UserSystemMessage;
import ru.otus.services.DBServiceUser;

@Configuration
public class MessageSystemConfig {
    @Autowired
    private DBServiceUser usersService;

    public static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem getMessageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public HandlersStore getRequestHandlerDatabaseStore() {
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, getGetUserDataRequestHandler());
        return requestHandlerDatabaseStore;
    }

    @Bean
    public RequestHandler<UserSystemMessage> getGetUserDataRequestHandler() {
        return new GetUserDataRequestHandler(usersService);
    }

    @Bean
    public RequestHandler<UserSystemMessage> getGetUserDataResponseHandler() {
        return new GetUserDataResponseHandler(getCallbackRegistry());
    }

    @Bean
    public CallbackRegistry getCallbackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean
    public MsClient getDatabaseMsClient() {
        MsClientImpl databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                getMessageSystem(), getRequestHandlerDatabaseStore(), getCallbackRegistry());
        getMessageSystem().addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean
    public HandlersStore getRequestHandlerFrontendStore() {
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, getGetUserDataResponseHandler());
        return requestHandlerFrontendStore;
    }

    @Bean
    public MsClient getFrontendMsClient() {
        MsClientImpl frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                getMessageSystem(), getRequestHandlerFrontendStore(), getCallbackRegistry());
        getMessageSystem().addClient(frontendMsClient);
        return frontendMsClient;
    }
}