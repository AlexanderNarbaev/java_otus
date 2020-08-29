package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.dao.UserDao;
import ru.otus.handlers.GetUserDataRequestHandler;
import ru.otus.handlers.GetUserDataResponseHandler;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.messagesystem.*;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.User;
import ru.otus.model.UserSystemMessage;
import ru.otus.services.DBServiceUser;
import ru.otus.services.DbServiceUserImpl;
import ru.otus.sessionmanager.SessionManager;

@Configuration
public class MessageSystemConfig {
    @Autowired
    private DBServiceUser usersService;

    public static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Autowired
    MessageSystem messageSystem;

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
    public DBServiceUser getDBServiceUser(UserDao userDao, HwCache<Long, User> userHwCache) {
        return new DbServiceUserImpl(userDao, userHwCache);
    }

    @Bean
    public UserDao getUserDao(SessionManager sessionManager) {
        return new UserDaoHibernate(sessionManager);
    }

    @Bean
    public SessionManager geSessionManager(SessionFactory sessionFactory) {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public HwCache<Long, User> geHwCache() {
        return new MyCache<Long, User>();
    }
}