package ru.otus.grpc.server;

import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.protobuf.generated.Empty;
import ru.otus.messagesystem.protobuf.generated.UserMessage;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;
import ru.otus.model.User;
import ru.otus.services.DBServiceUser;

import java.util.Collections;
import java.util.List;

@Service
public class DBUserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final DBServiceUser dbServiceUser;

    @Autowired
    public DBUserServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void saveUser(UserMessage request, StreamObserver<UserMessage> responseObserver) {
        User user = new User(0L, request.getName(), request.getLogin(), request.getPassword());
        user.setId(dbServiceUser.saveUser(user));
        responseObserver.onNext(userConvertToUserMessage(user));
        responseObserver.onCompleted();
    }

    private UserMessage userConvertToUserMessage(User user) {
        return UserMessage.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .build();
    }

    @Override
    public void findAllUsers(Empty request, StreamObserver<UserMessage> responseObserver) {
        List<User> users = dbServiceUser.getUsers().orElse(Collections.emptyList());
        users.forEach(user -> responseObserver.onNext(userConvertToUserMessage(user)));
        responseObserver.onCompleted();
    }
}
