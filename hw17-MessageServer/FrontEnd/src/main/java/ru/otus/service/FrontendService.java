package ru.otus.service;

import ru.otus.model.UserFront;

import java.util.List;
import java.util.Optional;

public interface FrontendService {
    void saveUser(UserFront userData);

    Optional<List<UserFront>> getUsers();
}

