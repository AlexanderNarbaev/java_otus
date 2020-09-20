package ru.otus.service;

import ru.otus.messagesystem.protobuf.generated.Empty;
import ru.otus.messagesystem.protobuf.generated.UserMessage;
import ru.otus.messagesystem.protobuf.generated.UserServiceGrpc;
import ru.otus.model.UserFront;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class FrontendServiceImpl implements FrontendService {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public FrontendServiceImpl(UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub) {
        this.userServiceBlockingStub = userServiceBlockingStub;
    }

    @Override
    public void saveUser(UserFront userData) {
        userServiceBlockingStub.saveUser(UserMessage
                .newBuilder()
                .setId(userData.getId())
                .setName(userData.getName())
                .setLogin(userData.getLogin())
                .setPassword(userData.getPassword())
                .build());
    }

    @Override
    public Optional<List<UserFront>> getUsers() {
        Iterator<UserMessage> allUsers = userServiceBlockingStub.findAllUsers(Empty.newBuilder().build());
        List<UserFront> returnUsers = new ArrayList<>();
        allUsers.forEachRemaining(userMessage -> returnUsers.add(
                new UserFront(
                        userMessage.getId(),
                        userMessage.getName(),
                        userMessage.getLogin(),
                        userMessage.getPassword())));
        return Optional.of(returnUsers);
    }
}
