package ru.otus;

import io.grpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.*;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.handlers.GetUserDataRequestHandler;
import ru.otus.messagesystem.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagesystem.model.UserSystemMessage;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;
import ru.otus.messagesystem.service.MessageUserServiceImpl;

import java.io.IOException;

@Configuration
public class MessageSystemConfig {

    @Autowired
    private MessageSystem messageSystem;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private MessageUserServiceImpl messageUserService;

    public static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";


    @Bean(destroyMethod = "dispose")
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
        return new GetUserDataRequestHandler(createClient());
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
                messageSystem, getRequestHandlerDatabaseStore(), getCallbackRegistry());
        messageSystem.addClient(databaseMsClient);
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
                messageSystem, getRequestHandlerFrontendStore(), getCallbackRegistry());
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub createClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(applicationProperties.getGrpcClientHost(), Integer.parseInt(applicationProperties.getGrpcClientPort()))
                .usePlaintext()
                .build();
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public void startGrpcServer() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(
                Integer.parseInt(
                        applicationProperties.getGrpcServerPort()))
                .addService(messageUserService).build();
        server.start();
        server.awaitTermination();
    }

}