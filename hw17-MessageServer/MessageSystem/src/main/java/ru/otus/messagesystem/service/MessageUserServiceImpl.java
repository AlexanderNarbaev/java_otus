package ru.otus.messagesystem.service;

import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.MessageSystemConfig;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagesystem.model.UserSystemMessage;
import ru.otus.messagesystem.protobuf.generated.Empty;
import ru.otus.messagesystem.protobuf.generated.UserMessage;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;

import java.util.Iterator;

@Service
public class MessageUserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    private final MessageSystem messageSystem;

    @Autowired
    public MessageUserServiceImpl(UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub, MessageSystem messageSystem) {
        this.userServiceBlockingStub = userServiceBlockingStub;
        this.messageSystem = messageSystem;
    }

    @Override
    public void saveUser(UserMessage request, StreamObserver<UserMessage> responseObserver) {

        MsClient frontendClient = messageSystem.getClient(MessageSystemConfig.FRONTEND_SERVICE_CLIENT_NAME);
        Message outMsg = frontendClient.produceMessage(
                MessageSystemConfig.DATABASE_SERVICE_CLIENT_NAME,
                new UserSystemMessage(request),
                MessageType.USER_DATA, callback -> responseObserver.onNext(request));
        frontendClient.sendMessage(outMsg);

        responseObserver.onCompleted();
    }

    @Override
    public void findAllUsers(Empty request, StreamObserver<UserMessage> responseObserver) {
        Iterator<UserMessage> allUsers = userServiceBlockingStub.findAllUsers(Empty.newBuilder().build());
        allUsers.forEachRemaining(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}